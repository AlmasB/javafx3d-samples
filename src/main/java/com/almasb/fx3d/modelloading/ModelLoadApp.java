package com.almasb.fx3d.modelloading;

import com.interactivemesh.jfx.importer.obj.ObjModelImporter;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.shape.MeshView;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public class ModelLoadApp extends Application {

    private Scene createScene() {

        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-3.5);

        Group model = loadModel(getClass().getResource("Scooter-smgrps.obj"));
        model.getTransforms().add(new Rotate(90, Rotate.Y_AXIS));

        animate(model);

        Group root = new Group(model);

        Scene scene = new Scene(root, 1280, 720, true);
        scene.setCamera(camera);

        return scene;
    }

    private Group loadModel(URL url) {
        Group modelRoot = new Group();

        ObjModelImporter importer = new ObjModelImporter();
        importer.read(url);

        for (MeshView view : importer.getImport()) {
            modelRoot.getChildren().add(view);
        }

        return modelRoot;
    }

    private void animate(Group model) {
        model.getChildren()
                .stream()
                .filter(view -> view.getId().equals("RimFront") || view.getId().equals("RimRear"))
                .forEach(view -> {
                    RotateTransition rt = new RotateTransition(Duration.seconds(0.33), view);
                    rt.setCycleCount(Integer.MAX_VALUE);
                    rt.setAxis(Rotate.X_AXIS);
                    rt.setByAngle(360);
                    rt.setInterpolator(Interpolator.LINEAR);
                    rt.play();
                });
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(createScene());
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
