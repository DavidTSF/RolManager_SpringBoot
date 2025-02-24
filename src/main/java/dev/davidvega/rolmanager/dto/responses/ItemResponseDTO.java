package dev.davidvega.rolmanager.dto.responses;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ItemResponseDTO {
    private Integer itemId;
    private String name;
    private String description;
    private String type;
    private Integer value;
    private BigDecimal weight;
}