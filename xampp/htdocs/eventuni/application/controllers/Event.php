<?php

defined('BASEPATH') OR exit('No direct script access allowed');

require APPPATH . '/libraries/REST_Controller.php';
use Restserver\Libraries\REST_Controller;

class Event extends REST_Controller {

    function __construct($config = 'rest') {
        parent::__construct($config);
        $this->load->database();
    }

    //Menampilkan data event
    function index_get() {
        $event = $this->db->get('event')->result();
        $this->response(array("result"=>$event, 200));
    }
    
    //Mengirim atau menambah data event baru
    function index_post() {
		
		$flag=$this->post('flag');
		
		//Insert
		if($flag=="INSERT")
		{	
			//Config Upload
			$config['upload_path'] = './assets/files/event/';
			$config['allowed_types'] = '*';
			$config['max_size'] = '0';
			$image = $_FILES['image']['name'];
			$path="./assets/files/event/";
			$this->load->library('upload', $config);
			
			if (!$this->upload->do_upload('image')) 
			{
				$this->response(array('status' => 'fail', 502));
			} 
			else 
			{
				$data = array(
							'id'=> $this->post('id'),
							'name' => $this->post('name'),
							'description'=> $this->post('description'),
							'longdescription'=> $this->post('longdescription'),
							'kategori'=> $this->post('kategori'),
							'kegiatan'=> $this->post('kegiatan'),
							'image'=> $image,
							'lokasi'=> $this->post('lokasi'),
							'tiket'=> $this->post('tiket'),
							'waktu'=> $this->post('waktu'),
							'cp'=> $this->post('cp'),
							'link'=> $this->post('link'));
				$insert = $this->db->insert('event', $data);
				$this->response($data, 200);
			}
		}
		else if($flag=="UPDATE") //Update
		{
			//Config Upload
			$config['upload_path'] = './assets/files/event/';
			$config['allowed_types'] = '*';
			$config['max_size'] = '0';
			$path="./assets/files/event/";
			$image = $_FILES['image']['name'];
			$this->load->library('upload', $config);
			
			if (!$this->upload->do_upload('image')) 
			{
				$this->response(array('status' => 'fail', 502));
			} 
			else 
			{
				$id = $this->post('id');
				
				//Hapus Image Lama
				$queryimg = $this->db->query("SELECT image FROM `".$this->db->dbprefix('event')."` WHERE id='".$id."'");
				$row = $queryimg->row();
				$picturepath="./assets/files/event/".$row->image;	
				unlink($picturepath);
				
				$data = array(
					'id'=> $this->post('id'),
					'name' => $this->post('name'),
					'description'=> $this->post('description'),
					'longdescription'=> $this->post('longdescription'),
					'kategori'=> $this->post('kategori'),
					'kegiatan'=> $this->post('kegiatan'),
					'image'=> $image,
					'lokasi'=> $this->post('lokasi'),
					'tiket'=> $this->post('tiket'),
					'waktu'=> $this->post('waktu'),
					'cp'=> $this->post('cp'),
					'link'=> $this->post('link'));
				$this->db->where('id', $id);
				$update = $this->db->update('event', $data);
				$this->response($data, 200);	
			}
		}	
    }

    //Menghapus salah satu data event
    function index_delete() {
        $id = $this->delete('id');
		
		//Hapus Image Lama
		$queryimg = $this->db->query("SELECT image FROM `".$this->db->dbprefix('event')."` WHERE id='".$id."'");
		$row = $queryimg->row();
		$picturepath="./assets/files/event/".$row->image;	
		unlink($picturepath);
		
        $this->db->where('id', $id);
        $delete = $this->db->delete('event');
        if ($delete) {
            $this->response(array('status' => 'success'), 201);
        } else {
            $this->response(array('status' => 'fail', 502));
        }
    }

}
?>