package Interface;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class Logincontrole {
    private Parent view;
    private TextField username;
    private PasswordField password;
    private Label errorLabel;
    private Runnable onSignupClick;
    private Runnable onForgotPasswordClick;
    private LoginCallback onLoginAttempt;
    public interface LoginCallback {
        void onLogin(String username, String password);
    }
    public Logincontrole() {
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #4b3aa8, #2b7cd3);");
        VBox card = new VBox(15);
        card.setPadding(new Insets(30));
        card.setAlignment(Pos.TOP_CENTER);
        card.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 20;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 15, 0, 0, 4);"
        );
        card.setMaxWidth(300);
        Label title = new Label("Login");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: #d32f2f; -fx-font-size: 12px;");
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);
        username = new TextField();
        username.setPromptText("Username");
        username.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-border-width: 0 0 1 0;" +
                        "-fx-border-color: #aaa;" +
                        "-fx-padding: 5;"
        );
        password = new PasswordField();
        password.setPromptText("Password");
        password.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-border-width: 0 0 1 0;" +
                        "-fx-border-color: #aaa;" +
                        "-fx-padding: 5;"
        );
        password.setOnAction(e -> handleLogin());
        Label forgot = new Label("Forget Password?");
        forgot.setStyle("-fx-text-fill: gray; -fx-font-size: 12px; -fx-cursor: hand;");
        forgot.setOnMouseEntered(e -> forgot.setStyle("-fx-text-fill: #2b7cd3; -fx-font-size: 12px; -fx-cursor: hand;"));
        forgot.setOnMouseExited(e -> forgot.setStyle("-fx-text-fill: gray; -fx-font-size: 12px; -fx-cursor: hand;"));
        forgot.setOnMouseClicked(e -> handleForgotPassword());
        Button loginBtn = new Button("Login");
        loginBtn.setPrefWidth(200);
        loginBtn.setStyle(
                "-fx-background-color: white;" +
                        "-fx-border-radius: 30;" +
                        "-fx-background-radius: 30;" +
                        "-fx-border-color: black;" +
                        "-fx-font-size: 14px;" +
                        "-fx-padding: 5 10;"
        );
        loginBtn.setOnAction(e -> handleLogin());
        loginBtn.setOnMouseEntered(e -> loginBtn.setStyle(
                "-fx-background-color: #f0f0f0;" +
                        "-fx-border-radius: 30;" +
                        "-fx-background-radius: 30;" +
                        "-fx-border-color: black;" +
                        "-fx-font-size: 14px;" +
                        "-fx-padding: 5 10;"
        ));
        loginBtn.setOnMouseExited(e -> loginBtn.setStyle(
                "-fx-background-color: white;" +
                        "-fx-border-radius: 30;" +
                        "-fx-background-radius: 30;" +
                        "-fx-border-color: black;" +
                        "-fx-font-size: 14px;" +
                        "-fx-padding: 5 10;"
        ));
        HBox signupBox = new HBox(5);
        signupBox.setAlignment(Pos.CENTER);
        Label notMember = new Label("Not a Member ?");
        notMember.setStyle("-fx-text-fill: gray; -fx-font-size: 12px;");
        Label signup = new Label("Signup");
        signup.setStyle("-fx-text-fill: #2b7cd3; -fx-font-size: 12px; -fx-cursor: hand;");
        signup.setOnMouseEntered(e -> signup.setStyle("-fx-text-fill: #4b3aa8; -fx-font-size: 12px; -fx-cursor: hand;"));
        signup.setOnMouseExited(e -> signup.setStyle("-fx-text-fill: #2b7cd3; -fx-font-size: 12px; -fx-cursor: hand;"));
        signup.setOnMouseClicked(e -> handleSignup());
        signupBox.getChildren().addAll(notMember, signup);
        card.getChildren().addAll(
                title,
                errorLabel,
                username,
                password,
                forgot,
                loginBtn,
                signupBox
        );
        root.getChildren().add(card);
        view = root;
    }
    private void handleLogin() {
        String user = username.getText().trim();
        String pass = password.getText();
        if (!validateInputs(user, pass)) {
            return;
        }
        hideError();
        if (onLoginAttempt != null) {
            onLoginAttempt.onLogin(user, pass);
        } else {
            System.out.println("Login attempt - Username: " + user);
            showError("Login callback not configured!");
        }
    }
    private boolean validateInputs(String user, String pass) {
        if (user.isEmpty()) {
            showError("Username cannot be empty!");
            username.requestFocus();
            return false;
        }
        if (pass.isEmpty()) {
            showError("Password cannot be empty!");
            password.requestFocus();
            return false;
        }
        if (user.length() < 3) {
            showError("Username must be at least 3 characters!");
            username.requestFocus();
            return false;
        }
        if (pass.length() < 6) {
            showError("Password must be at least 6 characters!");
            password.requestFocus();
            return false;
        }
        return true;
    }
    private void handleSignup() {
        if (onSignupClick != null) {
            onSignupClick.run();
        } else {
            System.out.println("Signup clicked - callback not configured");
        }
    }
    private void handleForgotPassword() {
        if (onForgotPasswordClick != null) {
            onForgotPasswordClick.run();
        } else {
            System.out.println("Forgot password clicked - callback not configured");
        }
    }
    public void showError(String message){
        errorLabel.setText(message);
        errorLabel.setVisible(true);
        errorLabel.setManaged(true);
    }
    public void hideError() {
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);
    }
    public void clearForm() {
        username.clear();
        password.clear();
        hideError();
    }
    public void setOnLoginAttempt(LoginCallback callback) {
        this.onLoginAttempt = callback;
    }
    public void setOnSignupClick(Runnable callback) {
        this.onSignupClick = callback;
    }
    public void setOnForgotPasswordClick(Runnable callback) {
        this.onForgotPasswordClick = callback;
    }
    public Parent getView() {
        return view;
    }
    public String getUsername() {
        return username.getText().trim();
    }
    public String getPassword() {
        return password.getText();
    }
}