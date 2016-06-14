package joolz.test;

import com.liferay.faces.portal.context.LiferayFacesContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class MyBean {
	private static final Log LOG = LogFactoryUtil.getLog(MyBean.class);

	public void doSearch() {
		int count;
		final int active = WorkflowConstants.STATUS_APPROVED;
		final LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		final LiferayFacesContext lfc = LiferayFacesContext.getInstance();

		try {
			final List<User> users = UserLocalServiceUtil.getUsers(-1, -1);
			LOG.debug("==================================");
			LOG.debug("All users:");
			for (final User user : users) {
				LOG.debug(user.getScreenName() + " - " + user.getFirstName() + " - " + user.getMiddleName() + " - "
						+ user.getLastName() + " - " + user.getEmailAddress() + " - ");
			}

			final User user = UserLocalServiceUtil.getUserByScreenName(lfc.getCompanyId(), "jal");
			LOG.debug("==================================");
			LOG.debug("Got user " + user.getFirstName() + ", " + user.getMiddleName() + ", " + user.getLastName()
					+ ", " + user.getScreenName() + ", " + user.getEmailAddress());
			LOG.debug("Total number of users " + UserLocalServiceUtil.getUsersCount());

			final List<String> keywordsTest = new ArrayList<String>(Arrays.asList("a", "al", "alb", "l", "lb", "lbe",
					"lberts", "a*", "al*", "alb*", "l*", "lb*", "lbe*", "lberts*"));
			LOG.debug("companyId " + lfc.getCompanyId());
			LOG.debug("active " + 0);
			LOG.debug("params " + params);

			LOG.debug("----------------------------------");
			for (final String kw : keywordsTest) {
				count = UserLocalServiceUtil.searchCount(lfc.getCompanyId(), kw, active, params);
				LOG.debug("Count with keywords " + kw + ": " + count);
			}
		} catch (final PortalException | SystemException e) {
			e.printStackTrace();
		}
	}

}
