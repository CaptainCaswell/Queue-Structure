import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import java.io.PrintStream;
import java.io.OutputStream;
import java.io.IOException;

public class ServiceGUI extends Application {

    // Load Queue Processor
    private QueueProcessor qp = new QueueProcessor();

    // Initilize variables
    private TextArea idleAgents;
    private TextArea waitingCustomers;
    private TextArea announcements;
    private TextField nameInput;
    private Label counterLabel;
    private ListView<QueueProcessor.Agent> occupiedAgents;

    public void start( Stage primaryStage ) {
        // Redirect terminal errors to announcements
        PrintStream terminal = new PrintStream( new OutputStream() {
            public void write(int b) throws IOException {
                announcements.appendText( String.valueOf( (char) b) );
            }
        } );

        System.setOut( terminal );

        // Set window title
        primaryStage.setTitle( "Service Queue 3000" );

        // Main layout
        BorderPane main = new BorderPane();
        main.setPadding( new Insets( 15 ) );

            // Top layout
            VBox top = new VBox( 10 );

                // Title
                Label title = new Label( "Queue Management Controls" );
                title.setStyle( "-fx-font-size: 18 px; -fx-font-weight: bold;" );

                // Control layout
                HBox controls = new HBox( 10 );

                    // Add Agent Button
                    Button addAgentButton = new Button( "Add Agent" );
                    addAgentButton.setOnAction (e -> {
                        QueueProcessor.Agent agent = qp.new Agent();
                        qp.addAgent( agent );
                        updateDisplay();
                    });

                    // Add Client Box/Button
                    nameInput = new TextField();
                    nameInput.setPromptText( "Enter next client name" );
                    nameInput.setPrefWidth( 200 );

                    Button addClientButton = new Button ( "Add Client" );
                    addClientButton.setOnAction ( e -> {
                        String name = nameInput.getText().trim();
                        if ( !name.isEmpty() ) {
                            qp.addClient( new Customer( name ) );
                            nameInput.clear();
                            updateDisplay();
                        }
                    });

                    // Serve Next Client Button
                    Button serveButton = new Button( "Serve Next Client" );
                    serveButton.setOnAction (e -> {
                        QueueProcessor.Agent agent = qp.serveClient();
                        if ( agent != null ) {
                            String announcement = qp.makeAnnouncement( agent );
                            announcements.appendText( announcement + "\n" );
                        }
                        
                        else {
                            if ( qp.getIdleAgents().isEmpty() ) {

                            }
                        }
                        updateDisplay();
                    });

                // Assemble Control Panel
                controls.getChildren().addAll(
                    addAgentButton,
                    nameInput,
                    addClientButton,
                    serveButton
                );
            
            top.getChildren().addAll( title, controls );

            // Center Layout
            GridPane center = new GridPane();

            center.setHgap( 10 );
            center.setVgap( 10 );
            center.setPadding( new Insets( 10, 0, 10, 0 ) ); // Top and bottom padding

                // Idle Agents List
                VBox idleBox = createTextAreaSection( "Open Stations" );
                idleAgents = (TextArea) idleBox.getChildren().get( 1 );

                // Occupied Agents ListView
                VBox occupiedBox = createListViewSection( "Being Served" );

                    // Agent Done Button
                    Button markDoneButton = new Button( "Mark Selected Agent As Done" );
                    markDoneButton.setMaxWidth( Integer.MAX_VALUE );

                    markDoneButton.setOnAction ( e -> {
                        QueueProcessor.Agent selected = occupiedAgents.getSelectionModel().getSelectedItem();
                        if ( selected != null ) {
                            qp.getOccupiedAgents().remove( selected );
                            qp.addAgent( selected );
                            announcements.appendText( "Agent at  station " + selected.getStation() + " finished serving customer" );
                            updateDisplay();
                        }
                    });

                occupiedBox.getChildren().add( markDoneButton );

                occupiedAgents = (ListView<QueueProcessor.Agent>) occupiedBox.getChildren().get( 1 );

                // Waiting Customer List
                VBox waitingBox = createTextAreaSection( "Waiting Queue" );
                waitingCustomers = (TextArea) waitingBox.getChildren().get( 1 );

            center.add( idleBox, 0, 0 );
            center.add( occupiedBox, 1, 0 );
            center.add( waitingBox, 2, 0 );

            // Equal columns
            ColumnConstraints colconst = new ColumnConstraints();
            colconst.setPercentWidth( 33.33 );
            center.getColumnConstraints().addAll( colconst, colconst, colconst );

            // Bottom layout
            VBox bottom = new VBox( 10 );

                // Announcements
                Label announcementsLabel = new Label( "Announcements" );
                announcementsLabel.setStyle( "-fx-font-weight: bold; -fx-font-size: 14px;" );
                
                announcements = new TextArea();
                announcements.setEditable( false );
                announcements.setPrefHeight( 100 );
                announcements.setWrapText( true );

                // Counter
                counterLabel = new Label( "Initializing..." );
                counterLabel.setStyle("-fx-font-size: 14px; -fx-padding: 5px;");

            bottom.getChildren().addAll(announcementsLabel, announcements, counterLabel );



        main.setTop( top );
        main.setCenter( center );
        main.setBottom( bottom );
        
        // Scene
        Scene scene = new Scene( main, 1000, 650 );
        primaryStage.setScene( scene );
        primaryStage.show();

        // Display
        updateDisplay();
    }

    private VBox createTextAreaSection( String title ) {
        VBox box = new VBox( 5 );
        
        Label label = new Label( title );
        label.setStyle( "-fx-font-weight: bold; -fx-font-size: 14px;" );
        
        TextArea area = new TextArea();
        area.setEditable( false );
        area.setWrapText( true );
        area.setPrefHeight( 340 );
        
        box.getChildren().addAll( label, area );
        return box;
    }

    private VBox createListViewSection( String title ) {
        VBox box = new VBox( 5 );
        
        Label label = new Label(title);
        label.setStyle( "-fx-font-weight: bold; -fx-font-size: 14px;" );
        
        ListView<QueueProcessor.Agent> listView = new ListView<>();
        listView.setPrefHeight( 310 );
        listView.getSelectionModel().setSelectionMode( SelectionMode.SINGLE );
        
        box.getChildren().addAll( label, listView );
        return box;
    }

    private void updateDisplay() {
        StringBuilder idle = new StringBuilder();
        StringBuilder wait = new StringBuilder();

        // Idle
        for ( QueueProcessor.Agent agent : qp.getIdleAgents() ) {
            idle.append( agent.getStation() ).append( "\n" );
        }

        // Check for no idle
        if ( idle.length() == 0 ) {
            idle.append( "None" );
        }

        idleAgents.setText( idle.toString() );

        // Occupied
        occupiedAgents.getItems().clear();

        for ( QueueProcessor.Agent agent : qp.getOccupiedAgents() ) {
            occupiedAgents.getItems().add( agent );
        }

        // Waiting
        for( Client client : qp.getClients() ) {
            wait.append( client.toString() ).append( "\n" );
        }

        // Check for no waiting
        if ( wait.length() == 0 ) {
            wait.append( " None" );
        }

        waitingCustomers.setText( wait.toString() );

        // Counter
        counterLabel.setText( String.format(
            "Idle: %d | Occupied: %d | Waiting: %d",
            qp.getIdleAgents().size(),
            qp.getOccupiedAgents().size(),
            qp.getClients().size()
        ) );
    }

    public static void main( String[] args ) {
        launch( args );
    }

    
}