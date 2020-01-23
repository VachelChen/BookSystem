        function checkLogon(){     
            var username = $('#username').val();
            var password = $('#password').val();
            var data = {
                "username": username,
                "password": password
            };

            $.ajax({
                url: '/login',
                type: 'POST',
                data: data,
                success: function (data, status) {
                    if (status == 'success') {
                        location.href = 'main';
                    }
                },
                error: function (data, status, e) {
                    if (status == 'error') {
                        alert("用户名或密码错误");
                        location.href = '/';
                    }
                }
            });
        }

        function checkReset(){
        document.getElementById("email").value = "";
        document.getElementById("password").value = "";//判断非空
            alert("已清空输入！");
        }

        function Hint(){
            alert("密码在数据库中的存储均采用加密存储，若遗忘请联系：\r\n信息17-1 陈嘉辉");
        }

        function send() {
            var receiver = $('#receiver').val();
            var topic = $('#topic').val();
            var context = $('#context').val();
            var data = {
                "receiver": receiver,
                "topic": topic,
                "context":context
            };

            $.ajax({
                url: '/sendMailno',
                type: 'POST',
                data: data,
                success: function (data, status) {
                    if (status == 'success') {
                        alert("发送成功！");
                    }
                },
                error: function (data, status, e) {
                    if (status == 'error') {
                        alert("发送信息有误，请重新输入");
                        location.href = '/send';
                    }
                }
            });

        }
      





