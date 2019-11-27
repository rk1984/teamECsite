<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import ="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<link rel="stylesheet" href="./css/button.css">
<link rel="stylesheet" href="./css/commonMessage.css">
<link rel="stylesheet" href="./css/header_h1.css">
<link rel="stylesheet" href="./css/pumpkinimg.css?<%=new Random() %>">

<title>商品一覧</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="contents">
<h1>商品一覧画面</h1>
<!-- 	doCheck()の引数に当てはまらない記号類が入った場合に実行されるif文 -->
	<s:if test="keywordsErrorMessageList != null && keywordsErrorMessageList.size() > 0">
		<div class="error">
			<div class="error-message">
				<s:iterator value="keywordsErrorMessageList"><s:property /><br></s:iterator>
			</div>
		</div>
	</s:if>
<!-- 	productInfoListに該当する商品がある場合に実行されるif文 -->
	<s:elseif test="productInfoDTOList != null && productInfoDTOList.size() > 0">
		<table class="product-list-table">
			<s:iterator value="productInfoDTOList" status="st">
				<s:if test="#st.index%3 == 0"><tr></s:if>
					<td>
						<a href ='<s:url action="ProductDetailsAction"><s:param name="productId" value="%{productId}"/></s:url>'>
							<img src='<s:property value="imageFilePath"/>/<s:property value="imageFileName"/>'/><br>
							<s:property value="productName"/><br>
							<s:property value="productNameKana"/><br>
							<s:property value="price"/>円<br>
						</a>
					</td>
					<%-- 閉じタブしか入ってないと言われているが、上記の<s:if test="#st.index%3 == 0"><tr></s:if>に開始タブがすでにあるので無視してOK --%>
					<s:if test="#st.index%3 == 2"></tr></s:if>
			</s:iterator>
		</table>
	</s:elseif>
	<s:else>
<!-- 	上記のelseif条件に該当しない場合はこれを実行 -->
		<div class="info">
			検索結果がありません。
		</div>
	</s:else>
</div>
</body>
</html>