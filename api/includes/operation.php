<?php

class DbOperation
{
    private $con;

    function __construct()
    {
        require_once dirname(__FILE__) . '/connect.php';

        $db = new DbConnect();
        $this->con = $db->connect();
    }

    // Adicionando a função de login
    function login($email, $password)
    {
        $stmt = $this->con->prepare("SELECT user_id FROM users WHERE email = ? AND senha = ?");
        $stmt->bind_param("ss", $email, $password);
        $stmt->execute();
        $stmt->store_result();

        return $stmt->num_rows > 0;
    }
}

$response = array();

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    // Obter dados da requisição POST
    $postData = json_decode(file_get_contents('php://input'), true);

    if (isset($postData['email']) && isset($postData['password'])) {
        $email = $postData['email'];
        $password = $postData['password'];

        $db = new DbOperation();

        if ($db->login($email, $password)) {
            $response['success'] = true;
            $response['message'] = "Login successful";
        } else {
            $response['success'] = false;
            $response['message'] = "Invalid email or password";
        }
    } else {
        $response['success'] = false;
        $response['message'] = "Required fields are missing";
    }
} else {
    $response['success'] = false;
    $response['message'] = "Invalid request";
}

echo json_encode($response);
?>
