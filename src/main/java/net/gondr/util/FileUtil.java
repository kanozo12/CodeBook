package net.gondr.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.springframework.util.FileCopyUtils;

public class FileUtil {
	private static String makeThumbnail(String uploadPath, String filename) throws Exception {
		BufferedImage sourceImage = ImageIO.read(new File(uploadPath, filename));

		BufferedImage destImage = Scalr.resize(sourceImage, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, 128);

		String thumbnailName = uploadPath + File.separator + "s_" + filename;

		File newFile = new File(thumbnailName);
		// 확장자 끄집어 내고
		String extension = filename.substring(filename.lastIndexOf(".") + 1);
		// 새로운 파일에 확장자로 다시 써줌.
		ImageIO.write(destImage, extension.toUpperCase(), newFile);

		return thumbnailName.substring(uploadPath.length()).replace(File.separatorChar, '/');
	}

	public static String uploadFile(String uploadPath, String originalName, byte[] fileData) throws Exception {
		UUID uid = UUID.randomUUID(); // 고유이름을 위한 랜덤 아이디

		// 고유한 이름으로 만들어준다.
		String saveName = uid.toString() + "_" + originalName;

		File upDir = new File(uploadPath); // 업로드할 디렉토리 잡아주고
		if (!upDir.exists()) {
			// 해당 경로가 없다면 생성한다.
			upDir.mkdir();
		}
		// 업로드 경로에 생성한 파일명으로 만들어주고
		File target = new File(uploadPath, saveName);
		// 업로드된 파일데이터를 생성한 파일로 복사해준다.
		FileCopyUtils.copy(fileData, target);
		// FileCopyUtils는 pom.xml에서 가져온 라이브러리이다

		// 확장자 알아내고
		String extension = originalName.substring(originalName.lastIndexOf(".") + 1);

		String uploadFilename = null;
		if (MediaUtil.getMediaType(extension) != null) {
			// 이미지 파일이 업로드 된 거라면
			uploadFilename = makeThumbnail(uploadPath, saveName);
		} else {
			uploadFilename = (uploadPath + File.separator + saveName).replace(File.separatorChar, '/');
		}

		// 저장된 파일명을 보내준다.
		return uploadFilename;
	}
}
