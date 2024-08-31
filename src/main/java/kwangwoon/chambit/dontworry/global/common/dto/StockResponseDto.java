package kwangwoon.chambit.dontworry.global.common.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.util.List;

@Data
public class StockResponseDto {
    private List<JsonNode> output;
    private String rt_cd;
    private String msg_cd;
    private String msg1;
}
