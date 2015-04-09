<?php
//Testfil
require_once('model.php');
$model = new Model();

$id = 17;
$name = "Kalle";
$address = "gatan 22";
$zipcode = '11111';
$town = "byn";
$website = "www";
$logotype = "minlogga";
$longitude = 12.1212;
$latitude = 11.3133;
$PutArray = Array("kalle25", "gatan", '4949', "byn", "dsvc", "logga", 4949, 4940);
$model->Update($PutArray, $id); 

//$model->Create($name, $address, $zipcode, $town, $website, $logotype, $longitude, $latitude);
//$model->Create($id, $name, $address, $zipcode, $town, $website, $logotype, $longitude, $latitude);


//$model->Create($id, $name, $address, $zipcode, $town, $website, $logotype, $longitude, $latitude);
//echo $model->GetPositions($id);
