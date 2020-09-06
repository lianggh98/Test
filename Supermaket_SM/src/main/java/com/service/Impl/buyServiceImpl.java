package com.service.Impl;

import com.Bean.Buy.Buy;
import com.BeanVo.BuyVo;
import com.dao.buyMapper;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.service.buyServiceInter;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import view.new_conotroller.buy.Controller_inTable;
import view.util.Manage;
import view.util.ViewAssistImpl;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @ClassName
 * @Description
 * @Author LGH
 * @Date 2020/7/15 10:00
 * @Version 1.0
 **/

@Service("buyService")
public class buyServiceImpl implements buyServiceInter{

    @Autowired
    buyMapper mapper;
    @Autowired
    ViewAssistImpl viewAssist;
    /**  Sync from Mysql date**/
    private static ObservableList<BuyVo> BuyDate= FXCollections.observableArrayList();
    public  <T> void setBuyCellValueFactory(JFXTreeTableColumn<BuyVo, T> column, Function<BuyVo, ObservableValue<T>> mapper) {
        column.setCellValueFactory((TreeTableColumn.CellDataFeatures<BuyVo, T> param) -> {
            if (column.validateValue(param)) {
                return mapper.apply(param.getValue().getValue());
            } else {
                return column.getComputedValue(param);
            }
        });
    }
    @Override
    public void set_BuyJFXTreeTableColumn(JFXTreeTableColumn... column) {
//    设置数据源

        setBuyCellValueFactory(column[0], BuyVo -> new SimpleBooleanProperty(BuyVo.getSelected()));
        setBuyCellValueFactory(column[1], BuyVo -> new SimpleIntegerProperty(BuyVo.getBuyid()).asObject());
        setBuyCellValueFactory(column[2], BuyVo -> new SimpleIntegerProperty(BuyVo.getSupplier()).asObject());
        setBuyCellValueFactory(column[3], BuyVo -> new SimpleIntegerProperty(BuyVo.getPid()).asObject());
        setBuyCellValueFactory(column[4], BuyVo -> new SimpleIntegerProperty(BuyVo.getGid()).asObject());
        setBuyCellValueFactory(column[5], BuyVo -> new SimpleStringProperty(BuyVo.getGname()));
        setBuyCellValueFactory(column[6], BuyVo -> new SimpleStringProperty(BuyVo.getModel()));
        setBuyCellValueFactory(column[7],   BuyVo -> new SimpleStringProperty(BuyVo.getQuantity()));
        setBuyCellValueFactory(column[8], BuyVo -> new SimpleStringProperty(BuyVo.getPname()));
        setBuyCellValueFactory(column[9], BuyVo -> new SimpleStringProperty(BuyVo.getComeTime().toString()));
        setBuyCellValueFactory(column[10], BuyVo -> new SimpleStringProperty(BuyVo.getProduced().toString()));
        setBuyCellValueFactory(column[11], BuyVo -> new SimpleDoubleProperty(BuyVo.getPrice()).asObject());
        setBuyCellValueFactory(column[12], BuyVo -> new SimpleDoubleProperty(BuyVo.getPrices()).asObject());
        setBuyCellValueFactory(column[13], BuyVo -> new SimpleStringProperty(BuyVo.getBuyid().toString()));
        setBuyCellValueFactory(column[14], BuyVo -> new SimpleStringProperty(BuyVo.getBuyid().toString()));
        setBuyCellValueFactory(column[15], BuyVo -> new SimpleStringProperty(BuyVo.getExpiration()));
        /**
         * @Description:设置set为button居中
         */
        column[13].setCellFactory(new Callback<TreeTableColumn<BuyVo, String>, TreeTableCell<BuyVo, String>>() {
            @Override
            public TreeTableCell<BuyVo, String> call(TreeTableColumn<BuyVo, String> buyTreeTableView) {
                TreeTableCell<BuyVo, String> tc = new TreeTableCell<BuyVo, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        setText(null);
                        setGraphic(null);
                        if (item != null) {
                            JFXButton jfxButton = new JFXButton("已入库");
                            if(this.getTreeTableRow()!=null) {
                                //创建按钮
                                if(integers.size()==0){
                                    jfxButton.setText("待入库");
                                    jfxButton.setStyle("-fx-background-color: #FF4040;-fx-text-fill: white");
                                    jfxButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                        @Override
                                        public void handle(MouseEvent mouseEvent) {
                                            Controller_inTable.setBuyVo(BuyDate.get(getIndex()));
                                            try {
                                                viewAssist.getNO_Title_Stage(420,350,"入库信息补齐", "/new_fxml/buy/inTable.fxml","/new_images/shop_car.png",null).show();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                }else
                                for(Integer i:integers){
                                    /** **/
                                    if(BuyDate.get(getIndex()).getBuyid()==i){
                                        jfxButton = new JFXButton("已入库");
                                        setGraphic(jfxButton);
                                        return;
                                    }else{
                                        jfxButton.setText("待入库");
                                        jfxButton.setStyle("-fx-background-color: #FF4040;-fx-text-fill: white");
                                        jfxButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                            @Override
                                            public void handle(MouseEvent mouseEvent) {
                                                Controller_inTable.setBuyVo(BuyDate.get(getIndex()));
                                                try {
                                                    viewAssist.getNO_Title_Stage(420,350,"入库信息补齐", "/new_fxml/buy/inTable.fxml","/new_images/shop_car.png",null).show();
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                                    }
                                }

                            }

                            setGraphic(jfxButton);
                        }
                    }
                };
                tc.setAlignment(Pos.CENTER);
                return tc;
            }
        });

        column[14].setCellFactory(new Callback<TreeTableColumn<BuyVo, String>, TreeTableCell<BuyVo, String>>() {
            @Override
            public TreeTableCell<BuyVo, String> call(TreeTableColumn<BuyVo, String> buyTreeTableView) {
                TreeTableCell<BuyVo, String> tc = new TreeTableCell<BuyVo, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        setText(null);
                        setGraphic(null);
                        if (item != null) {
                            JFXButton jfxButton = new JFXButton("修改");
                            jfxButton.setStyle("-fx-background-color: #3fc1c9;-fx-text-fill: #f5f5f5");
                            if(this.getTreeTableRow()!=null) {
                                jfxButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                    @Override
                                    public void handle(MouseEvent event) {
//                                      获取这一行的信息
                                        BuyVo buyVo = BuyDate.get(getIndex());
                                        System.out.println(buyVo);
//                                      提交到修改页面
                                        try {

                                            Manage.getController_buy_update_insert().setTemp(1);
//                                            設置數據源
                                            Manage.getController_buy_update_insert().setBuyV(buyVo);
                                            viewAssist.getNO_Title_Stage(730,600,"測試", "/new_fxml/buy/buyAdd.fxml","/new_images/shop_car.png",null).show();
                                       } catch (IOException e) {
                                            e.printStackTrace();
                                        }

//                                      修改
                                    }
                                });


                            }

                            setGraphic(jfxButton);
                        }
                    }
                };
                tc.setAlignment(Pos.CENTER);
                return tc;
            }
        });

        column[0].setEditable(true);
        column[0].setCellFactory(new Callback<TreeTableColumn<BuyVo, Boolean>, TreeTableCell<BuyVo, Boolean>>() {
            @Override
            public TreeTableCell<BuyVo, Boolean> call(TreeTableColumn<BuyVo, Boolean> buyTreeTableView) {
                TreeTableCell<BuyVo, Boolean> tc = new TreeTableCell<BuyVo, Boolean>() {
                    @Override
                    public void updateItem(Boolean item, boolean empty) {
                        //清空样式
                        setText(null);
                        setGraphic(null);
                        if (item != null) {
                            CheckBox checkBox = new CheckBox();
                            setGraphic(checkBox);
                            if (this.getTreeTableRow() != null) {
                                BuyVo g = BuyDate.get(getIndex());
                                checkBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                    @Override
                                    public void handle(MouseEvent mouseEvent) {
                                        g.setSelected(checkBox.selectedProperty().getValue());
                                    }
                                });
                            }
                        }

                    }
                };
                tc.setAlignment(Pos.CENTER);
                return tc;

            }

        });
    }
    private List<Integer> integers = new ArrayList<>();
    @Override
    public void show_BuyJFXTreeTableColumn(){
        integers = mapper.findinTableByBuyId();
        BuyDate= FXCollections.observableArrayList();
//        从数据库获取数据
        for(BuyVo p :mapper.findBuyVoAll()){
            BuyDate.add(p);
        }
        //          存入TreeTableView
        Manage.getController_buy().buyTreeTableView.setRoot(new RecursiveTreeItem<>(BuyDate, RecursiveTreeObject::getChildren));
//        关闭主节点的显示
        Manage.getController_buy().buyTreeTableView.setShowRoot(false);
    }
    @Override
    public int Buyselected(){
        List<Integer> list = new ArrayList<>();
        for(BuyVo b:BuyDate)
            if (b.getSelected())
                list.add(b.getBuyid());

        if(list.size()!=0) {
            mapper.deleteBuyByIds(list);
            return 1;
        }
        return list.size();
    }
    @Override
    public void update_inser(String type, List<String> strings, Timestamp inTimeStamp,Timestamp producedTimeStamp){
        Buy buy = new Buy();
        List<Buy> list = new ArrayList<>();
        buy.setBuyid(Integer.valueOf(strings.get(0)));
        buy.setGid(Integer.valueOf(strings.get(1)));
        buy.setModel(strings.get(2));
        buy.setQuantity(strings.get(3));
        buy.setPrice(Double.valueOf(strings.get(4)));
        buy.setPrices(Double.valueOf(strings.get(5)));
        buy.setSupplier(Integer.valueOf(strings.get(6)));
        buy.setPid(Integer.valueOf(strings.get(7)));
        buy.setExpiration(strings.get(8));
        buy.setComeTime(inTimeStamp);
        buy.setProduced(producedTimeStamp);
        list.add(buy);
        switch (type){
            case "update":
                        mapper.updateBuy(buy);
                        show_BuyJFXTreeTableColumn();
                break;
            case "insert":
                        mapper.insertBuys(list);
                        show_BuyJFXTreeTableColumn();
                break;
        }
    }
    @Override
    public List<Integer> get_EveryDayBuyRecord(List<String> strings){
        List<Integer> integerList = new ArrayList<>();
        for(String s:strings){
            String startTime = s+" 00:00:00";
            String endTime = s+" 23:59:00";
            Map<String,String> map  =new HashMap();
            map.put("startTime",startTime);
            map.put("endTime",endTime);
            Integer temp  = mapper.findEveryDayBuyRecord(map).size();
           if( temp==null)
                integerList.add(0);
           else
               integerList.add(temp);
        }
        return integerList;
    }

}