<?php
require_once("action.php");


class NavigationView {
	const action = "action";
	
	public function getAction(){
		if (isset($_GET[NavigationView::action])){
			return $_GET[NavigationView::action];
		}
		else {
			return action:: GetAvSidan;
		}
	}   
	
	public function createMenu(){
		return
		'
			<li><a href="index.php?'.NavigationView::action.'='.action::CREATE_MESSAGE.'">Skapa meddelanden</a></li>
			<li><a href="index.php?'.NavigationView::action.'='.action::SEARCH.'">Sök meddelanden</a></li>
			<li><a href="index.php?'.NavigationView::action.'='.action::CHANGE_AUTHORITY.'">Ändra behörighet</a></li></ul>
		';			
	}   
}