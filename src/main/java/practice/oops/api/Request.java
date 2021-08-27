package practice.oops.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;


@AllArgsConstructor
@Data
@Getter
@Setter
@Builder
public class Request {

    Map<String, String> params;
}
