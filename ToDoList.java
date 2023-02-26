import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javafx.scene.text.Text;
import java.time.LocalDateTime;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Background;
/**
 * This class is an extension of Application that creates a To-Do List.
 * @author 903655720 rbadari3
 * @version 1.0
 */
public class ToDoList extends Application {
    private Label actualNumber = null;
    private int count = 0;
    private LocalDateTime time;
    private ObservableList<String> options = FXCollections.observableArrayList("1", "2", "3", "4", "5");
    private ComboBox<String> comboBox = new ComboBox<String>(options);
    private ObservableList<String> options2 = FXCollections.observableArrayList("Study", "Shop", "Cook", "Sleep");
    private ComboBox<String> comboBox2 = new ComboBox<String>(options2);
    private ArrayList<String> listOfTaskNames = new ArrayList<String>();
    private ArrayList<String> listOfNumHours = new ArrayList<String>();
    private ArrayList<String> listOfTypeTask = new ArrayList<String>();
    private ArrayList<LocalDateTime> listOfTimes = new ArrayList<LocalDateTime>();
    private TextField taskField;
    private boolean taskIsAlreadyAdded = false;
    private boolean taskWithSameNameAndType = false;
    private boolean taskNameAlreadyExists = false;

    /**
     * Alert if there is no name of task, task type, or number of hours.
     */
    public void noneOfRequiredFieldsAlert() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        String temp = "Please type the name of the task, select its type,";
        temp += " and select the hours needed to complete it.";
        alert.setContentText(temp);
        alert.showAndWait();
    }
    /**
     * Alert if there is no name of task or task type.
     */
    public void noNameOfTaskOrTaskTypeAlert() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Please type the name of the task and select its type.");
        alert.showAndWait();
    }
    /**
     * Alert if there is no name of task or number of hours.
     */
    public void noNameOfTaskOrNumberOfHoursAlert() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Please type the name of the task and select the hours needed to complete it.");
        alert.showAndWait();
    }
    /**
     * Alert if there is no task type or number of hours.
     */
    public void noTaskTypeOrNumberOfHoursAlert() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Please select the type of task and the hours needed to complete the task.");
        alert.showAndWait();
    }
    /**
     * Alert if there is no name of task.
     */
    public void noTaskNameAlert() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Please enter the name of the task.");
        alert.showAndWait();
    }
    /**
     * Alert if there is no task type.
     */
    public void noTaskTypeAlert() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Please select the type of the task.");
        alert.showAndWait();
    }
    /**
     * Alert if there is no number of hours.
     */
    public void noNumberOfHoursAlert() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Please select the number of hours needed to complete the task.");
        alert.showAndWait();
    }
    /**
     * Alert if task is already added.
     */
    public void taskIsAlreadyAddedAlert() {
        taskIsAlreadyAdded = true;
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("This task has already been added.");
        alert.showAndWait();
        clearAllFields();
    }
    /**
     * Alert if task name already exists.
     */
    public void taskNameAlreadyExistsAlert() {
        taskNameAlreadyExists = true;
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("This task name already exists.");
        alert.showAndWait();
        clearAllFields();
    }
    /**
     * Method to clear task name and both drop-down menus.
     */
    public void clearAllFields() {
        taskField.clear();
        comboBox.setValue(null);
        comboBox2.setValue(null);
    }

    @Override
    public void start(Stage primaryStage) {
        //Making the name of the application window and a title for my application.
        primaryStage.setTitle("To-Do List");
        Text title = new Text(400, 50, "To-Do List");
        title.setFont(Font.font("Times New Roman", FontWeight.BOLD, 40));
        title.setFill(Color.rgb(179, 163, 105));
        //Making a list for my tasks, getting its size, and initializing my textField.
        SimpleListProperty<String> names = new SimpleListProperty<>(FXCollections.observableArrayList());
        IntegerProperty intProperty = new SimpleIntegerProperty();
        intProperty.bind(names.sizeProperty());
        taskField = new TextField("");
        //Making the Enqueue Task Button.
        Button btn2 = new Button();
        btn2.setText("Enqueue Task");
        btn2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                taskIsAlreadyAdded = false;
                taskWithSameNameAndType = false;
                taskNameAlreadyExists = false;
                String nameOfTask = taskField.getText();
                String numOfHours = (String) comboBox.getValue();
                String typeOfTask = (String) comboBox2.getValue();
                if ((nameOfTask.equals("")) && (numOfHours == null) && (typeOfTask == null)) {
                    noneOfRequiredFieldsAlert();
                } else if ((nameOfTask.equals("")) && (numOfHours == null)) {
                    noNameOfTaskOrNumberOfHoursAlert();
                } else if ((nameOfTask.equals("")) && (typeOfTask == null)) {
                    noNameOfTaskOrTaskTypeAlert();
                } else if ((numOfHours == null) && (typeOfTask == null)) {
                    noTaskTypeOrNumberOfHoursAlert();
                } else if ((nameOfTask.equals(""))) {
                    noTaskNameAlert();
                } else if ((numOfHours == null)) {
                    noNumberOfHoursAlert();
                } else if ((typeOfTask == null)) {
                    noTaskTypeAlert();
                } else {
                    for (int i = 0; i < listOfTaskNames.size(); i++) {
                        if (nameOfTask.equals(listOfTaskNames.get(i))) {
                            if (typeOfTask.equals(listOfTypeTask.get(i))) {
                                if (numOfHours.equals(listOfNumHours.get(i))) {
                                    taskIsAlreadyAddedAlert();
                                }
                                DateTimeFormatter dtf2;
                                dtf2 = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
                                String temp5 = "Task: " + nameOfTask + "   -   " + "Type: " + typeOfTask;
                                temp5 += "   -   " + "Complete by ";
                                temp5 += dtf2.format(listOfTimes.get(i).plusHours(Integer.parseInt(numOfHours)));
                                names.set(i, temp5);
                                taskWithSameNameAndType = true;
                                listOfNumHours.set(i, numOfHours);
                                clearAllFields();
                            }
                            if (!(taskIsAlreadyAdded) && !(taskWithSameNameAndType)) {
                                taskNameAlreadyExistsAlert();
                            }
                        }
                    }
                    if (!(taskIsAlreadyAdded) && !(taskWithSameNameAndType) && !(taskNameAlreadyExists)) {
                        listOfTaskNames.add(nameOfTask);
                        listOfNumHours.add(numOfHours);
                        listOfTypeTask.add(typeOfTask);
                        time = LocalDateTime.now();
                        listOfTimes.add(time);
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
                        nameOfTask = "Task: " + nameOfTask + "   -   " + "Type: " + typeOfTask;
                        nameOfTask += "   -   " + "Complete by ";
                        nameOfTask += dtf.format(time.plusHours(Integer.parseInt(numOfHours)));
                        clearAllFields();
                        names.add(nameOfTask);
                        String temp4 = "\t\t\t\t\tNumber of Tasks: " + intProperty.getValue();
                        temp4 += " \t\t\t\t\t\t\t\tNumber of Tasks Completed: " + count;
                        actualNumber.setText(temp4);
                    }
                }
            }
        });
        //Making the Dequeue Task Button.
        Button btn = new Button();
        btn.setText("Dequeue Task");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    names.remove(0);
                    listOfTaskNames.remove(0);
                    listOfTypeTask.remove(0);
                    listOfNumHours.remove(0);
                    listOfTimes.remove(0);
                    count++;
                    String temp3 = "\t\t\t\t\tNumber of Tasks: " + intProperty.getValue();
                    temp3 += " \t\t\t\t\t\t\t\tNumber of Tasks Completed: " + count;
                    actualNumber.setText(temp3);
                } catch (Exception e) {
                    String temp2 = "\t\t\t\t\tNumber of Tasks: " + intProperty.getValue();
                    temp2 += " \t\t\t\t\t\t\t\tNumber of Tasks Completed: " + count;
                    actualNumber.setText(temp2);
                }
            }
        });
        //Making the Number of Tasks and Number of Tasks Completed.
        String temp = "\t\t\t\t\tNumber of Tasks: " + intProperty.getValue();
        temp += " \t\t\t\t\t\t\t\tNumber of Tasks Completed: " + count;
        actualNumber = new Label(temp);
        actualNumber.setFont(Font.font("Times New Roman", 20));
        HBox taskCompletedBox = new HBox();
        taskCompletedBox.getChildren().addAll(actualNumber);
        //Adding the title to the top of the BorderPane.
        BorderPane p = new BorderPane();
        HBox top = new HBox();
        top.setBackground(new Background(new BackgroundFill(Color.rgb(0, 48, 87), CornerRadii.EMPTY, Insets.EMPTY)));
        top.setPadding(new Insets(0, 10, 0, 10));
        top.getChildren().addAll(title);
        top.setAlignment(Pos.CENTER);
        p.setTop(top);
        //Adding items to the bottom of the BorderPane.
        HBox bottom = new HBox();
        bottom.setBackground(new Background(new BackgroundFill(Color.rgb(0, 48, 87), CornerRadii.EMPTY, Insets.EMPTY)));
        bottom.setPadding(new Insets(30, 30, 30, 30));
        Label spacing = new Label("     Number of Hours Needed: ");
        spacing.setTextFill(Color.rgb(195, 201, 212));
        spacing.setFont(Font.font("Times New Roman", 20));
        Label spacing2 = new Label("           ");
        Label spacing3 = new Label("           ");
        Label spacing4 = new Label("     Task Type: ");
        spacing4.setTextFill(Color.rgb(195, 201, 212));
        spacing4.setFont(Font.font("Times New Roman", 20));
        Label spacing5 = new Label("           Task Name: ");
        spacing5.setTextFill(Color.rgb(195, 201, 212));
        spacing5.setFont(Font.font("Times New Roman", 20));
        bottom.getChildren().addAll(spacing5, taskField, spacing4, comboBox2);
        bottom.getChildren().addAll(spacing, comboBox, spacing2, btn2, spacing3, btn);
        p.setBottom(bottom);
        //Making a ListView object to show my tasks and putting it in the center of my BorderPane.
        ListView<String> listView = new ListView<String>(names);
        VBox center = new VBox();
        BackgroundFill fill = new BackgroundFill(Color.rgb(179, 163, 105), CornerRadii.EMPTY, Insets.EMPTY);
        center.setBackground(new Background(fill));
        center.setPadding(new Insets(20, 30, 30, 30));
        Label spacing6 = new Label("\t\t\t\t\t\t\t");
        Label spacing7 = new Label("\t\t\t\t\t\t\t");
        center.getChildren().addAll(taskCompletedBox, spacing6, listView);
        center.setAlignment(Pos.CENTER);
        p.setCenter(center);
        //Creating and setting the scene.
        Scene scene = new Scene(p, 1200, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    /**
     * Main method that launches args from parameter.
     * @param args Takes String from command prompt.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
