Click the src folder and then Main.java. To try a different method, change line 10. The options are:
arctan_of_1(); - double
arctan_of_1_over_root_3(); - BigDecimal
arctan_of_1_over_root_3_double(); - double
vietes(); - BigDecimal
vietes_double(); - double

To adjust the error goal for a method that works in doubles, there should be something along the lines of >= Math.pow(10, -15) at the end of the line that starts with "while."
This means: run the code until the error is less than 10^-15. This example should have 14 digits correct.

For the BigDecimal methods, there should be a variable called errorGoal. You can adjust this as well.


Let me know if you get it to work for more digits!
