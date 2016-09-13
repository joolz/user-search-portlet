package joolz.test;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
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
	private static final LinkedHashMap<String, Object> PARAMS = new LinkedHashMap<String, Object>();
	private static final long COMPANY_ID = 20155;

	public void doSearch() {

		// assume User with the following credentials:
		// screenName: xyz firstName: Firstname lastName: Lastname emailAddress:
		// abcdefg@ijklm.nop

		try {
			List<User> users = UserLocalServiceUtil.getUsers(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
			LOG.info("===============================");
			LOG.info("All users:");
			for (final User user : users) {
				LOG.info(user.getScreenName() + " - " + user.getFirstName() + " - " + user.getMiddleName() + " - "
						+ user.getLastName() + " - " + user.getEmailAddress() + " - ");
			}

			searchLoop(
					"screenName",
					new ArrayList<String>(Arrays.asList("xyz", "xy", "xyz*", "xy*", "*xyz", "*yz", "y", "*y", "y*",
							"*y*", "**y**")));

			searchLoop(
					"firstName",
					new ArrayList<String>(Arrays.asList("Firstname", "Fi", "Firstname*", "Fi*", "*Firstname",
							"*irstname", "irstnam", "*irstnam", "irstnam*", "*irstnam*", "**irstnam**")));

			searchLoop(
					"emailAddress",
					new ArrayList<String>(Arrays.asList("abcdefg@ijklm.nop", "abcdefg@ijklm.n", "abcdefg@ijklm.nop*",
							"abcdefg@ijkl*", "*abcdefg@ijklm.nop", "*defg@ijklm.nop", "cdefg@ijklm.n",
							"*cdefg@ijklm.n", "cdefg@ijklm.n*", "*cdefg@ijklm.n*", "**cdefg@ijklm.n**")));

			searchLoop("firstName again",
					new ArrayList<String>(Arrays.asList("Firstname", "Firstnam", "Firstna", "Firstn", "First", "Firs")));

		} catch (final SystemException e) {
			e.printStackTrace();
		}
	}

	private void searchLoop(final String label, final List<String> keywordsTest) throws SystemException {
		LOG.info(label + " search ----------------------");
		for (final String kw : keywordsTest) {
			LOG.info("keyword "
					+ kw
					+ " number of users "
					+ UserLocalServiceUtil.search(COMPANY_ID, kw, WorkflowConstants.STATUS_APPROVED, PARAMS,
							QueryUtil.ALL_POS, QueryUtil.ALL_POS, (OrderByComparator) null).size());
		}
	}

}
