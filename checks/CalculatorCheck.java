import ca.comp3074.paycalculator.PayCalculator;
import java.math.BigDecimal;
public class CalculatorCheck {
 public static void main(String[] args) {
  check("30", "20", "10", "600", "0", "600", "60");
  check("40", "20", "10", "800", "0", "800", "80");
  check("45", "20", "10", "800", "150", "950", "80");
  check("40.5", "20", "10", "800", "15", "815", "80");
  check("0", "20", "100", "0", "0", "0", "0");
  check("1.5", "12.34", "7.5", "18.51", "0", "18.51", "1.38825");
  try { PayCalculator.calculate(new BigDecimal("1"), new BigDecimal("1"), new BigDecimal("101")); throw new AssertionError("Invalid tax accepted"); } catch (IllegalArgumentException expected) {}
  System.out.println("PASS: six calculation cases and invalid tax rejection");
 }
 static void check(String h, String r, String t, String... expected) {
  BigDecimal[] actual=PayCalculator.calculate(new BigDecimal(h),new BigDecimal(r),new BigDecimal(t));
  for(int i=0;i<4;i++) if(actual[i].compareTo(new BigDecimal(expected[i]))!=0) throw new AssertionError("Case "+h+" output "+i+": "+actual[i]);
 }
}
