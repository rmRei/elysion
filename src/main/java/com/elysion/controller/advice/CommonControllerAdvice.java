package com.elysion.controller.advice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * 共通コントローラアドバイスクラス
 *
 * @author mirei
 */
@ControllerAdvice
public class CommonControllerAdvice {

	/** switchParam */
	@Value("${switch.parameter}")
	private String switchParam;

	/**
	 * 静的ファイルのキャッシュ破棄用パラメータを設定
	 */
	@ModelAttribute("switchParam")
	public String addSwitchParam() {
		return switchParam;
	}

}
