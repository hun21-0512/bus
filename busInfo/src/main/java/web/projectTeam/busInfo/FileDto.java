package web.projectTeam.busInfo;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@Getter @Setter
public class FileDto {
	private String fileName;
	private String contentType;
}
