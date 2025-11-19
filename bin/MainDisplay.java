public class MainDisplay extends ADisplay{
    public void showMenu() {
        String output = """
                =========================
                Internship Management System
                1)Student
                2)Career Staff
                3)Company Representative
                4)Register as company representative
                5)Quit
                =========================
                """;
        System.out.println(output);
    }
}
