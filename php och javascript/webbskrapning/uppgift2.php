<?php
//Den här klassen har i uppgift att ta hand om den informationen som skickas från index.php och avgöra vilken fil som ska skapas etc.
require_once 'model.php';
$method = strtoupper($_SERVER['REQUEST_METHOD']); 
$model = new Model();

switch($method){
	case 'PUT':
		$_PUT = file_get_contents('php://input');  		
		if(isset($_GET['id'])){
			$id = $_GET['id'];
			echo $id;
			$idCheck = $model->GetProducer($id);
			if ($idCheck == 'false'){
				header('Status: 404 Not Found');
				echo "användaren fanns inte";
				return;
			}
			parse_str($_PUT, $params);
			if($model->Update($params, $id)){
				header('Status: 200 OK');
				header('Content-type: application/json'); 
			}
			
			else{
				header('Status: 404 Not Found');
				echo "Uppdateringen genomfördes aldrig";
			}
		}
		else{
			header('Status: 404 Not Found');
			echo "id måste anges";			
		}
		break;
		
	case 'POST':
			if($model->Create($_POST['name'], $_POST['address'], $_POST['zipcode'], $_POST['town'], $_POST['website'], $_POST['logotype'], $_POST['latitude'], $_POST['longitude'])){	
			header('Status: 201 CREATED');
			header('Content-type: application/json'); 
		}
		else{			
			header('Status: 404 Not Found'); 
		}
		break;	
	case 'GET':
		$id = (isset($_GET['id'])) ? $_GET['id'] : null;		
		$action = (isset($_GET['action'])) ? $_GET['action'] : null;	
		echo $id;	
		if($action == 'position'){		
			//exit;
			$positions = $model->GetPositions($id); 
			if ($positions == 'false'){
				header('Status: 404 Not Found');
				echo "ingen producent";
			}
			
			else{
				header('Status: 200 OK');
				header('Content-type: application/json');
				echo $positions;	
			}
						
		}
		else{					
			$producer = $model->GetProducer($id);
			if ($producer == 'false'){
				header('Status: 404 Not Found');
				echo "ingen producent";
			}		
			else{
				header('Status: 200 OK');
				header('Content-type: application/json');	
			}			
			echo $producer;
		}	
		break;

	case 'DELETE':
		if(isset($_GET['id'])){
			$idCheck = $model->GetProducer($_GET['id']);
			if ($idCheck == 'false'){
				header('Status: 404 Not Found');
				echo "användaren fanns inte";
				return;
			}
			header('Status: 200 OK');
			header('Content-type: application/json');
			$model->Delete($_GET['id']);
		}
		else{
			header('Status: 404 Not Found'); 
		}
	break;
}

