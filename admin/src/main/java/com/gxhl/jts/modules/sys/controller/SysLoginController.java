/**
 * Copyright 2018 http://github.com/micc010
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.gxhl.jts.modules.sys.controller;


import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.gxhl.jts.common.model.ResponseModel;
import com.gxhl.jts.common.utils.SecurityHolder;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 登录相关
 *
 * @author roger.li
 * @since 2018-03-30
 */
@Controller
public class SysLoginController {

    @Autowired
    private Producer producer;

    /**
     * @param response
     *
     * @throws IOException
     */
    @RequestMapping("captcha.jpg")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //生成文字验证码
        String text = producer.createText();
        //生成图片验证码
        BufferedImage image = producer.createImage(text);

        //TODO 关闭session没办法调用了
        request.setAttribute(Constants.KAPTCHA_SESSION_KEY, text);

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
    }

//    /**
//     * 登录
//     *
//     * @param username
//     * @param password
//     * @param captcha
//     *
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping(value = "login", method = RequestMethod.POST)
//    public ResponseModel login(String username, String password, String captcha) {
//        String kaptcha = ShiroUtils.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
//        if (!captcha.equalsIgnoreCase(kaptcha)) {
//            return ResponseModel.error("验证码不正确");
//        }
//
//        try {
//            Subject subject = ShiroUtils.getSubject();
//            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
//            subject.login(token);
//        } catch (UnknownAccountException e) {
//            return ResponseModel.error(e.getMessage());
//        } catch (IncorrectCredentialsException e) {
//            return ResponseModel.error("账号或密码不正确");
//        } catch (LockedAccountException e) {
//            return ResponseModel.error("账号已被锁定,请联系管理员");
//        } catch (AuthenticationException e) {
//            return ResponseModel.error("账户验证失败");
//        }
//
//        return ResponseModel.ok();
//    }

//    /**
//     * 退出
//     *
//     * @return
//     */
//    @RequestMapping(value = "logout", method = RequestMethod.GET)
//    public String logout() {
//        ShiroUtils.logout();
//        return "redirect:login.html";
//    }

}
