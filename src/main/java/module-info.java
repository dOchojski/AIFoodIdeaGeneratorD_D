module com.d_d.aifoodideageneratord_d {
    requires javafx.controls;
    requires javafx.fxml;
            
                                requires com.almasb.fxgl.all;
    
    opens com.d_d.aifoodideageneratord_d to javafx.fxml;
    exports com.d_d.aifoodideageneratord_d;
}