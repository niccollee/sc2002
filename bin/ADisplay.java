/**
 * Lightweight base for console display helpers.
 *
 * Provides small, reusable display utilities for CLI screens.
 */

public abstract class ADisplay {
    
    /**
     * Print a short exit banner to stdout.
     */
    public void showQuit() {
        String output = """
                =========================
            
                        BYE! :)
            
                =========================

                """;
        System.out.println(output);
    }
}
