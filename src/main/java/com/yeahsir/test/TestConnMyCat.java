package com.yeahsir.test;

import com.yeahsir.utils.ConnUtils;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;
import java.sql.SQLException;

public class TestConnMyCat {

	public static void main(String[] args) {
		QueryRunner qr = new QueryRunner(ConnUtils.getDataSourceByDruid());
		String sql = "INSERT INTO tb_test(id,title) VALUES(100,'bb')";
		try {
			int result = qr.update(sql);
			System.out.println("result = " + result);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}

	/*@Override
	public int addGoodsToCart(Cart cart) {
		int result = -1;
		String sql = " INSERT INTO CART VALUES(?,?,?) ";
		Object[] param = {cart.getGid(), cart.getUid(), cart.getBuynum()};
		try {
			result = qr.update(sql, param);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}*/
}
