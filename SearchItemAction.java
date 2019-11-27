package com.internousdev.pumpkin.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.pumpkin.dao.MCategoryDAO;
import com.internousdev.pumpkin.dao.ProductInfoDAO;
import com.internousdev.pumpkin.dto.MCategoryDTO;
import com.internousdev.pumpkin.dto.ProductInfoDTO;
import com.internousdev.pumpkin.util.InputChecker;
import com.opensymphony.xwork2.ActionSupport;

public class SearchItemAction extends ActionSupport implements SessionAware {

	private String categoryId;
	private String keywords;
	private List<String> keywordsErrorMessageList;
	private List<ProductInfoDTO> productInfoDTOList;
	private Map<String, Object> session;

	public String execute(){
		//全てのカテゴリを定義
		if(categoryId == null){
			categoryId = "1";
		}

		InputChecker ic = new InputChecker();
		//処理用の変数に値を入れる
		if(StringUtils.isBlank(keywords)){
			//キーワードが null,""," ","　"の時に空文字に設定する
			keywords="";
		}else{
			//全角スペースを半角へ変換。二個以上の空白も半角スペース変換
			keywords = keywords.replaceAll("　", " ").replaceAll("\\s{2,}", " ").trim();
		}

		if(!keywords.equals("")){
			//InputCheckerのdoCkech()にある引数が条件に一致してるかを判断する
			keywordsErrorMessageList = ic.doCheck("検索ワード", keywords , 0, 50, true, true, true, true, true, true);
			//listに要素が1つ以上あればSUCCESSを返す
			if(keywordsErrorMessageList.size() > 0){
				return SUCCESS;
			}
		}

		ProductInfoDAO productInfoDAO = new ProductInfoDAO();

		switch(categoryId){
		//全てのカテゴリの場合はこれを実行
		case "1":
			productInfoDTOList = productInfoDAO.getProductInfoListByKeyword(keywords.split(" "));
			break;
		//特定のカテゴリが指定された場合はこれを実行
		default:
			productInfoDTOList = productInfoDAO.getProductInfoListByCategoryIdAndKeyword(categoryId, keywords.split(" "));
			break;
		}
		//セッションが切れた時にもう一度リストを作り直してセッションを保持する
		if(!session.containsKey("mCategoryDTOList")){
			List<MCategoryDTO> mCategoryDTOList = new ArrayList<MCategoryDTO>();
			MCategoryDAO mCategoryDAO = new MCategoryDAO();
			mCategoryDTOList = mCategoryDAO.getMCategoryList();

			session.put("mCategoryDTOList", mCategoryDTOList);
		}

		return SUCCESS;
	}

	public String getCategoryId(){
		return categoryId;
	}

	public void setCategoryId(String categoryId){
		this.categoryId = categoryId;
	}

	public String getKeywords(){
		return keywords;
	}

	public void setKeywords(String keywords){
		this.keywords = keywords;
	}

	public List<String> getKeywordsErrorMessageList(){
		return keywordsErrorMessageList;
	}

	public void setKeywordsErrorMessageList(List<String> keywordsErrorMessageList){
		this.keywordsErrorMessageList = keywordsErrorMessageList;
	}

	public List<ProductInfoDTO> getProductInfoDTOList(){
		return productInfoDTOList;
	}

	public void setProductInfoDTOList(List<ProductInfoDTO> productInfoDTOList){
		this.productInfoDTOList = productInfoDTOList;
	}

	public Map<String, Object> getSession(){
		return session;
	}

	public void setSession(Map<String, Object> session){
		this.session = session;
	}

}
