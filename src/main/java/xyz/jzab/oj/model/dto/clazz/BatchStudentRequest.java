package xyz.jzab.oj.model.dto.clazz;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

/**
 * @author JZAB
 * @from http://vip.jzab.xyz
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchStudentRequest {
    Integer clazzId;
    ArrayList<Integer> studentIds;
}
