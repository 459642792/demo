package boot.demo.entity.bo;

import lombok.Data;

@Data
public class ExcelMergedDTO {
    Integer mergedBeginRow;
    Integer mergedEndRow;
    Integer mergedBeginCol;
    Integer mergedEndCol;
}
