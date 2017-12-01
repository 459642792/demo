<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Spring boot Shiro Example </title>
</head>
<body>
...
<!-- Should be a direct child of BODY -->
<div class="login-screen">
    <!-- Default view-page layout -->
    <div class="view">
        <div class="page">
            <!-- page-content has additional login-screen content -->
            <div class="page-content login-screen-content">
                <div class="login-screen-title">My App</div>
                <!-- Login form -->
                <form action="/loginIndex" method="post">
                    <div class="list-block">
                        <ul>
                            <li class="item-content">
                                <div class="item-inner">
                                    <div class="item-title label">Username</div>
                                    <div class="item-input">
                                        <input type="text" name="username" placeholder="Username">
                                    </div>
                                </div>
                            </li>
                            <li class="item-content">
                                <div class="item-inner">
                                    <div class="item-title label">Password</div>
                                    <div class="item-input">
                                        <input type="password" name="password" placeholder="Password">
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                    <div class="list-block">
                        <ul>
                            <li>
                                <a href="/login" >Sign In</a>
                                <input type="submit" value="登录"/>
                            </li>
                        </ul>
                        <div class="list-block-labe">Some text with login information.</div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>