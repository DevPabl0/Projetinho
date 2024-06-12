<?php
 
class DbOperation
{
    private $con;
 
    function __construct()
    {
        require_once dirname(__FILE__) . '/DbConnect.php';
     
        $db = new DbConnect();
 
        $this->con = $db->connect();
    }

    // Adicionando a função de login
    function login($username, $password)
    {
        $stmt = $this->con->prepare("SELECT user_id FROM users WHERE username = ? AND password = ?");
        $stmt->bind_param("ss", $username, $password);
        $stmt->execute();
        $stmt->store_result();
        
        if ($stmt->num_rows > 0) {
            return true;
        } else {
            return false;
        }
    }
	
}