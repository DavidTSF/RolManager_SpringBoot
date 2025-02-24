package dev.davidvega.rolmanager.dto.requests;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ItemRequestDTO {
    private String name;
    private String description;
    private String type;
    private Integer value;
    private BigDecimal weight;
}