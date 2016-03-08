package com.yeepay.g3.app.soa.center.monitor.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Lists;
import com.yeepay.g3.app.soa.center.controller.BaseController;
import com.yeepay.g3.facade.soa.center.facade.AppMgtFacade;

/**
 * 应用依赖监控
 *
 * @author：wang.bao
 * @since：2014年9月4日 上午11:26:17
 * @version:
 */
@Controller
@RequestMapping("/monitor/dependencies")
public class DependenciesMonitorController extends BaseController {

	@Reference
	private AppMgtFacade appMgtFacade;

	@RequestMapping(value = { "" }, method = RequestMethod.GET)
	public String dependency(
			@RequestParam(value = "application", required = true) String application,
			@RequestParam(value = "reverse", required = false) Boolean reverse,
			@RequestParam(value = "deep", required = false) Integer deep,
			Model model) {
		try {
			if (deep == null || deep < 1) {
				deep = 2;
			}
			if (reverse == null) {
				reverse = false;
			}
			Map<String, Object> dependencies = appMgtFacade.findDependencies(
					application, reverse, deep);
			model.addAttribute("depCount", dependencies.size());
			model.addAttribute("dependencies",
					this.buildDependencyTree(1, dependencies));
		} catch (Exception e) {
			LOGGER.error("", e);
			model.addAttribute("depCount", 0);
			model.addAttribute("dependencies", Lists.newArrayList());
		}
		model.addAttribute("application", application);
		model.addAttribute("reverse", reverse);
		model.addAttribute("deep", deep);
		return "monitor/dependencies";
	}

	@SuppressWarnings("unchecked")
	private List<DependencyModel> buildDependencyTree(int level,
			Map<String, Object> dependencies) {
		List<DependencyModel> deps = Lists.newArrayList();
		if (dependencies != null && !dependencies.isEmpty()) {
			for (String app : dependencies.keySet()) {
				deps.add(new DependencyModel(level, app));
				deps.addAll(buildDependencyTree(level + 1,
						(Map<String, Object>) dependencies.get(app)));
			}
		}
		return deps;
	}

	public class DependencyModel {
		private int level;

		private String application;

		public DependencyModel(int level, String application) {
			this.level = level;
			this.application = application;
		}

		public int getLevel() {
			return level;
		}

		public void setLevel(int level) {
			this.level = level;
		}

		public String getApplication() {
			return application;
		}

		public void setApplication(String application) {
			this.application = application;
		}
	}
}
