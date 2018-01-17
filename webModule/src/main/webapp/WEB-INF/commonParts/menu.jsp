<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<nav class="navbar navbar-default">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a id="main_menu_home" class="navbar-brand" href="${pageContext.request.contextPath}/">EE Shop</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
      <c:if test="${user != null && canAdmin == true}">
        <li><a href="${pageContext.request.contextPath}/admin/">Администрировать</a></li>
      </c:if>
        <li><a href="${pageContext.request.contextPath}/news">Новости</a></li>
        <li><a href="${pageContext.request.contextPath}/catalog">Каталог</a></li>
        <li><a href="${pageContext.request.contextPath}/resetDB">Обнулить БД</a></li>

      </ul>

      <ul class="nav navbar-nav navbar-right">

        <c:choose>
            <c:when test="${user == null}" >
                <li><a href="${pageContext.request.contextPath}/login">Войти</a></li>
                <li><a href="${pageContext.request.contextPath}/register">Регистрация</a></li>
            </c:when>
            <c:otherwise>
                <li><a href="${pageContext.request.contextPath}/profile">Личный кабинет</a></li>
                <li><a href="${pageContext.request.contextPath}/logout">Выйти</a></li>
            </c:otherwise>
        </c:choose>
        <li><a href="#">Корзина <span class="badge">0</span></a></li>
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>


