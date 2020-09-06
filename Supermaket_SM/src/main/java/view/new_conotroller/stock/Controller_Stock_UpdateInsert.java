package view.new_conotroller.stock;

import com.BeanVo.StockVo;
import com.jfoenix.controls.*;
import com.service.Impl.stockServiceImpl;
import com.spring.BaseHolder;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import view.util.Manage;
import view.util.ViewAssistImpl;

import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;

public class Controller_Stock_UpdateInsert extends ViewAssistImpl implements Initializable {

    private static int temp = 0;
    public static void setTemp(int temp) {
        Controller_Stock_UpdateInsert.temp = temp;
    }
    private static StockVo stockVo;
    public static void setStockVo(StockVo stockVo) {
        Controller_Stock_UpdateInsert.stockVo = stockVo;
    }

    @FXML
    StackPane root;
    @FXML
    Label L_model,L_Dialog;
    @FXML
    JFXDialog dialog;
    @FXML
    JFXButton B_close,acceptButton;


    @FXML
    JFXTextField stockid,inid,wid,wname,quantity,gid,gname,expiration;
    @FXML
    JFXDatePicker producedDate;
    @FXML
    JFXTimePicker producedTime;

    private stockServiceImpl stockService;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stockService = BaseHolder.getApplicationContext().getBean("stockService", stockServiceImpl.class);

        stockid.setText(stockVo.getStockid().toString());
        inid.setText(stockVo.getInid().toString());
        wid.setText(stockVo.getWarehouseId().toString());
        wname.setText(stockVo.getWname());
        quantity.setText(stockVo.getQuantity());
        gid.setText(stockVo.getGid().toString());
        gname.setText(stockVo.getGname());
        expiration.setText(stockVo.getExpiration());
        String string = stockVo.getProduced().toString();
        producedDate.setValue(LocalDate.parse(string.substring(0, string.indexOf(" "))));
        producedTime.setValue(LocalTime.parse(string.substring(string.indexOf(" ")+1)));

    }


    @FXML
    public void B_update(){
        if(super.TextisNull(stockid,inid,wid,wname,quantity,gid,gname,expiration)==null){
            show_Dialog("输入框不能为空");
            return;
        }
        else if(!super.isnumber(stockid.getText())||!super.isnumber(inid.getText())||!super.isnumber(wid.getText())||!super.isnumber(gid.getText())){
            show_Dialog("输入的类型不匹配,请输入数字");
            return;
        }else if(super.getTime(producedDate,producedTime)==null){
            show_Dialog("请输入生产日期");
            return;
        }

        List<String> list = super.TextisNull(stockid,inid,quantity,wid,gid,expiration);
        Timestamp timestamp = super.getTime(producedDate,producedTime);
        stockService.updateStock(list,timestamp);
        Platform.runLater(()->{
            super.setMessageType(Manage.getController_stock().P_stock_Error,Manage.getController_stock().L_stock_Error,"message");
            Manage.getController_stock().L_stock_Error.setText("数据修改成功");
            stockService.show_JFXTreeTableColumn();
            B_closeMaster();
        });
    }
    @FXML
    public void B_closeMaster(){
        Stage stage = (Stage) B_close.getScene().getWindow();
        stage.close();
    }
    public void show_Dialog(String str){
        dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
        L_Dialog.setText(str);
        dialog.show(root);
    }

    @FXML
    public void auto_Gname(){
        if(super.isnumber(gid.getText()))
        super.autoSelectedGid(gid.getText(),gname);
        else
            show_Dialog("输入的类型有误,请输入数字");
    }

    @FXML
    public void auto_Wname(){
        if(super.isnumber(wid.getText()))
            super.autoSelectedWid(wid.getText(),wname);
        else
            show_Dialog("输入的类型有误,请输入数字");
    }
}