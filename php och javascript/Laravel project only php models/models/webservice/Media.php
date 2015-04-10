<?php

    class Media extends BaseModel {
    	
	protected $table = 'media';
		
		
	public static $rules = array(
		'email' => 'required|email|unique:customer',
		'password' => 'required|alpha_num|min:6|confirmed',
		'password_confirmation' => 'required|alpha_num|min:6'
	);
	
	/*
	public function places()
    {
        return $this->belongsTo('Place'); ???
    }	
	 * */
		
/*
	//Validate uploaded file
	//Separate images from videos
	//get and save thumbnails from the different files that have been updated. 
	
	public function createLogo(){
	$allowedExts = array("gif", "jpeg", "jpg", "png");
	$temp = explode(".", $_FILES["file"]["name"]);
	$extension = end($temp); //Nuvarande metod utgår ifrån filtillägg, vilket bör ändras längre fram.
	
	echo "extension = " . $extension;
	if(in_array($extension,$allowedExts) ) {
		echo '<br>allowed <br>';

	$width = $size[0];
	$height = $size[1];
	
	$temp = $_FILES["file"]["tmp_name"]; //sökvägen till filen på servern.
	$name = $_FILES["file"]["name"];
	
	$username = "testname";
	$logoname = "testLogoname";
	
	move_uploaded_file($temp, "../Logotypes/".$username); 
	$new_width = 120; 
	$new_height = 120; 
	$tmp_img = imagecreatetruecolor( $new_width, $new_height );
	 
	$im = imagecreatefromjpeg("../Logotypes/$username");
	imagecopyresampled($tmp_img, $im, 0, 0, 0, 0, $new_width, $new_height, $width, $height );
	imagepng($tmp_img, "../Logotypes/".$username);
	  
	 
	 //Spara sedan url:en i databasen.
	 
	 DB::insert('insert into customers(logourl) values(?), array($logoname)');
	 }
	 
	 else{
		echo "<br>extention not allowed<br>";
		//Kör en redirect till upload igen eller nåt.
	}
	 
	
	}
	
	public function editLogo(){
		//Användarnamn eller id kommer in som parameter.
		//Kör en read på nuvarande logotype.
		//Gör en delete på nuvarande logotype, om så önskas.
		//Eventuellt har företaget möjlighet att byta namn på logotypen.
		//Kalla återigen på CreateLogo();
		//Frågan är om man ens behöver en databassträng för logotypen om den ändå får samma namn som företagets namn.
		$logourl = "some random string";
		//$fortnoxid = 1; //or some other int
		DB::update('update customers set logourl = ? where fortnoxid = ?, array($logourl, $fortnoxid)'); //Se att variablerna och frågetecknen är rätt. 
	}
	
	public function viewLogo($username){
		echo "<img src='../Logotypes/$username'/>";
	}
	
	public function deleteLogo($username){
		unlink("../Logotypes/".$username); 
		//$fortnoxid = 1; //or some other int
		DB::delete('delete from customers(logoUrl) where fortnoxid = ?, array($fortnoxid)');
	}
 * */
 
}
 