package xyz.jzab.oj.ga;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author JZAB
 * @from http://vip.jzab.xyz
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GAPaper {
    GAQuestion[] questions;
    double fit;
    double p;

    public GAPaper(int paperNum){
        questions = new GAQuestion[paperNum];
    }
}
