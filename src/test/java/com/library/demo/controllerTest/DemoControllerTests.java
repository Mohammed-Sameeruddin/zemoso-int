package com.library.demo.controllerTest;

import com.library.demo.controller.DemoController;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
class DemoControllerTests {

    @Test
    void showHomeTest(){
        DemoController demoController = new DemoController();
        assertEquals("home",demoController.showHome());
    }

    @Test
    void showAccessDeniedTest(){
        DemoController demoController = new DemoController();
        assertEquals("access-denied",demoController.showAccessDenied());
    }

}
