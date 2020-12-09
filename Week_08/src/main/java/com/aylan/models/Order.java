package com.aylan.models;

import lombok.Data;
import lombok.AllArgsConstructor;

/**
 * created by Aylan
 * on 2020/12/9 10:21 下午
 */
@Data
@AllArgsConstructor
public class Order {

    private Long orderId;

    private Long userId;
}
