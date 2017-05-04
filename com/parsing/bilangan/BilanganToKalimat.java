package com.parsing.bilangan;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BilanganToKalimat extends Application {

	public String printBilanganToKalimat(long a) {

		HashMap<Integer, String> map1 = new HashMap<>();
		map1.put(1, "satu");
		map1.put(2, "dua");
		map1.put(3, "tiga");
		map1.put(4, "empat");
		map1.put(5, "lima");
		map1.put(6, "enam");
		map1.put(7, "tujuh");
		map1.put(8, "delapan");
		map1.put(9, "sembilan");
		map1.put(0, "");

		HashMap<Integer, String> map2 = new HashMap<>();
		map2.put(0, "");
		map2.put(1, "puluh");
		map2.put(2, "ratus");
		map2.put(3, "ribu");
		map2.put(4, "puluh ribu");
		map2.put(5, "ratus ribu");
		map2.put(6, "juta");
		map2.put(7, "puluh juta");
		map2.put(8, "ratus juta");
		map2.put(9, "miliar");
		map2.put(10, "puluh miliar");
		map2.put(11, "ratus miliar");
		map2.put(12, "triliun"); 
		map2.put(13, "puluh triliun"); 
		map2.put(14, "ratus triliun");
		map2.put(15, "ribu triliun"); 

		int  n , l;
		String st = Long.toString(a);
		l = n  = st.length() - 1;

		StringBuilder builder = new StringBuilder();

		ArrayList<String> list = new ArrayList<>();
		
		
		boolean belasan ; 

		while (n >= 0) {
			
			belasan = false; 
			int c = st.codePointAt(n);
			c = c - 48;
			String nama = map1.get(c) ; 
			String spasi  = " "; 
			
			if(c == 1 &&   l > n  && (l-n ) !=  6 && 
					(l-n) != 9 && 
					(l - n )!= 12 ){ // bukan digit satu miliar atau satu juta
									// atau satu triliun
				nama = "se"; 
				spasi = ""; 
				if( 
						(l > (l - n))  // jika masih ada digit lain
						&& (l - n ) == 3  // dan digit saat ini ribuan
				  ){
					nama = "satu"; 
					spasi = " "; 
				}
				
				// untuk digit yang mengandung sebutan belasan
				// sebut angka sebelumnya diikuti angka belas
				if( (l - n - 1) % 3 == 0 ){ 
					int c_ = st.codePointAt(n + 1);
					c_ = c_ - 48;
					if(c_ != 0 ){
						String nama_ = map1.get(c_)  ;
						if(nama_.equals("satu")){
							nama_ = "se"; 
						}else{
							nama_ = nama_ + " "; 
						}
						nama  = nama_ + "belas"; 
						spasi = " "; 
						belasan = true; 
					}
				}
			}
			
			// jika angka sebelumnya belasan tak usah sebut angka saat ini
			if (l > (l - n)) { 
				int c_1 = st.codePointAt(n - 1);
				c_1 = c_1 - 48;
				if (c_1 == 1) {
					if ((l - n) % 3 == 0) {
						nama = "";
						spasi = " ";
					}
				}

			}

			builder.append(nama);
			builder.append(spasi);

			if (c != 0) {
				if( ! belasan ){
					String sebutan = map2.get(l - n);
					String[] suku_sebutan = sebutan.split("\\s");
					// jika sudah bunyi 'juta', maka tak usah sebut 'puluh juta'
					// cukup 'puluh' saja
					if (suku_sebutan.length == 2) {
						if ( ! list.contains(suku_sebutan[1]) ) {
							builder.append(sebutan);
							list.add(suku_sebutan[1]);
						} else {
							builder.append(suku_sebutan[0]); 
						}
					} else {
						builder.append(sebutan);
						list.add(sebutan);
					}
				}

				builder.append("_");
			}
			n--;
		}

		String temp[] = builder.toString().split("\\_");
		StringBuilder bt = new StringBuilder();
		for (int i = temp.length - 1; i >= 0; i--) {
			bt.append(temp[i]);
			bt.append(" ");
		}
		String result = bt.toString().trim() ;
		result = result.replaceAll("\\s{2,}", " ");  

		return result ;
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		AnchorPane pane = new AnchorPane();
		pane.setPrefSize(400, 400);
		primaryStage.setScene(new Scene(pane));

		primaryStage.show();
		initLayout(pane);
	}

	TextArea  areahasil = new TextArea ();
	
	private void initLayout(AnchorPane pane) {
		
		VBox vbox1 = new VBox();
		vbox1.setSpacing(10);

		TextField inputbilangan = new TextField();
		inputbilangan.setPrefSize(150, 30);
		inputbilangan.setMaxSize(150, 30);

		vbox1.getChildren().add(inputbilangan);

		Button buttonconvert = new Button("CONVERT");
		buttonconvert.setPrefSize(150, 30);

		buttonconvert.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String c = inputbilangan.getText();
				try {
					long  a = Long.parseLong(c);
					String hasil = printBilanganToKalimat(a);
					areahasil.setText(hasil);  

				} catch (Exception e) {
					System.out.println("eror parsing int "); 
				}
			}
		});

		vbox1.getChildren().add(buttonconvert);
		
		VBox vbox2 = new VBox(); 
		
		areahasil.setPrefSize(200, 300); 
		areahasil.setWrapText(true); 
		vbox2.getChildren().add(areahasil);

		VBox finalbox = new VBox();
		finalbox.setTranslateX(5);
		finalbox.setTranslateY(5);
		finalbox.setSpacing(10);
		finalbox.getChildren().addAll(vbox1 , vbox2);

		pane.getChildren().add(finalbox);

	}
}