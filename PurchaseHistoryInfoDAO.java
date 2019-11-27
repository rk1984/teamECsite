package com.internousdev.pumpkin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.internousdev.pumpkin.dto.PurchaseHistoryInfoDTO;
import com.internousdev.pumpkin.util.DBConnector;

public class PurchaseHistoryInfoDAO {

	public List<PurchaseHistoryInfoDTO> getPurchaseHistoryList(String userId){

		DBConnector db = new DBConnector();
		Connection con = db.getConnection();
		List<PurchaseHistoryInfoDTO> purchaseHistoryInfoDTOList = new ArrayList<PurchaseHistoryInfoDTO>();

		String sql = "SELECT"
				+ " phi.id as id,"
				+ " phi.user_id as user_id,"
				+ " phi.product_count as product_count,"
				+ " pi.product_id as product_id,"
				+ " pi.product_name as product_name,"
				+ " pi.product_name_kana as product_name_kana,"
				+ " pi.product_description as product_description,"
				+ " pi.category_id as category_id,"
				+ " pi.image_file_name as image_file_name,"
				+ " pi.image_file_path as image_file_path,"
				+ " pi.release_company,"
				+ " pi.release_date,"
				+ " phi.price as price,"
				+ " phi.price * phi.product_count as subtotal,"
				+ " phi.regist_date as regist_date,"
				+ " di.family_name as family_name,"
				+ " di.first_name as first_name,"
				+ " di.user_address as user_address"
				+ " FROM purchase_history_info as phi"
				+ " LEFT JOIN product_info as pi"
				+ " ON phi.product_id = pi.product_id"
				+ " LEFT JOIN destination_info as di"
				+ " ON phi.destination_id = di.id"
				+ " WHERE phi.user_id = ?"
				+ " ORDER BY regist_date DESC";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, userId);
			ResultSet rs = ps.executeQuery();

			while(rs.next()){
				PurchaseHistoryInfoDTO purchaseHistoryInfoDTO = new PurchaseHistoryInfoDTO();
				purchaseHistoryInfoDTO.setId(rs.getInt("id"));
				purchaseHistoryInfoDTO.setUserId(rs.getString("user_id"));
				purchaseHistoryInfoDTO.setProductId(rs.getInt("product_id"));
				purchaseHistoryInfoDTO.setProductName(rs.getString("product_name"));
				purchaseHistoryInfoDTO.setProductNameKana(rs.getString("product_name_kana"));
				purchaseHistoryInfoDTO.setImageFileName(rs.getString("image_file_name"));
				purchaseHistoryInfoDTO.setImageFilePath(rs.getString("image_file_path"));
				purchaseHistoryInfoDTO.setReleaseCompany(rs.getString("release_company"));
				purchaseHistoryInfoDTO.setReleaseDate(rs.getDate("release_date"));
				purchaseHistoryInfoDTO.setPrice(rs.getInt("price"));
				purchaseHistoryInfoDTO.setProductCount(rs.getInt("product_count"));
				purchaseHistoryInfoDTO.setSubtotal(rs.getInt("subtotal"));
				purchaseHistoryInfoDTO.setFamilyName(rs.getString("family_name"));
				purchaseHistoryInfoDTO.setFirstName(rs.getString("first_name"));
				purchaseHistoryInfoDTO.setUserAddress(rs.getString("user_address"));
				purchaseHistoryInfoDTOList.add(purchaseHistoryInfoDTO);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try {
				con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}

		return purchaseHistoryInfoDTOList;
	}
	//購入履歴を登録するメソッド
	public int regist(String userId, int productId, int productCount, int destinationId, int price){

		DBConnector db = new DBConnector();
		Connection con = db.getConnection();

		String sql = "INSERT INTO purchase_history_info"
				+ "(user_id, product_id, product_count, price, destination_id, regist_date, update_date)"
				+ "VALUES (?, ?, ?, ?, ?, now(), now())";

		int count = 0;

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, userId);
			ps.setInt(2, productId);
			ps.setInt(3, productCount);
			ps.setInt(4, price);
			ps.setInt(5, destinationId);
			count = ps.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try {
				con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}

		return count;
	}
	//購入履歴を全削除するメソッド
	public int deleteAll(String userId){
		DBConnector db = new DBConnector();
		Connection con = db.getConnection();

		String sql = "DELETE FROM purchase_history_info WHERE user_id = ?";

		int count = 0;

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, userId);
			count = ps.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try {
				con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}

		return count;
	}

}
