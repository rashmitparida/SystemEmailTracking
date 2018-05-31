package SystemMailTracking.keywords;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import com.aventstack.extentreports.Status;



public class AppKeywords extends GenericKeywords{

	public void login(){
		test.log(Status.INFO, "Logging in"); 
		String username="";
		String password="";
		
		if(data.get("Username") == null){
			username=envProp.getProperty("defaultUsername");
			password=envProp.getProperty("defaultPass");
		}else{
			username=data.get("Username");
			password=data.get("Password");
		}
		getObject("userName_xpath").sendKeys(username);
		getObject("password_xpath").sendKeys(password);
		getObject("loginButton_xpath").click();
		test.log(Status.INFO, "Logging in with "+ data.get("Username")+"/"+data.get("Password"));
	}
	
		
	public void defaultLogin(){
		test.log(Status.INFO, "Logging in with default ID");

		String username=envProp.getProperty("adminusername");
		String password=envProp.getProperty("adminpassword");
		System.out.println("Default username "+username );
		System.out.println("Default password "+password );
	}
	
	//Verify Forgot Password Email sent successfully
	public void verifyEmailForForgotPassword()
	{
		
		try
		{
		test.log(Status.INFO, "Validating Forgot password Email");	
		click("ForgotPassword_xpath");
		type("ForgotPasswordEmail_xpath","Email");
		click("ForgotPasswordSubmitButton_xpath");
		test.log(Status.INFO, "Forgot Password email sent successfully");
		}
		catch (Throwable t)
		{
			reportFailure("Failed to send forgot password email" );
		}
	}
	
	//Verify the Success Message for forgot password email
	public void verifyForgotPasswordEmailSent()
	{
		test.log(Status.INFO, "Validating Forgot password Email");	
		boolean result = isElementPresent("SuccessMessageAfterForgotPwdEmail_xpath");	
		String actualText="";
		if(result)
		{
			actualText="An email has been sent to your email account with instructions to reset your password. Please check Junk/Spam folders for this message and wait up to 10 minutes for it.";
			test.log(Status.INFO, "Forgot password Email sent successfully with success message");
		}
		else
			actualText="Failure";
		
		if(!actualText.equals(data.get("ValidateText"))){
			test.log(Status.INFO, "Validating Forgot password Email");
			reportFailure("Got  result "+actualText );

		}
		
	}
	
	//Verify password reset works for user
	public void verifyResetPassword()
	{
		try
		{
			test.log(Status.INFO, "Validating forgot password email in mailinator");
			type("MailinatorMailinput_xpath","Email");
			click("MailinatorGoButton_xpath");
			click("MailinatorInbox_xpath");
			Thread.sleep(2000);
			click("MailinatorForgotPasswordMail_xpath");
			
			//Switch to iframe and locate the element
			driver.switchTo().frame(1);
			click("ChangePasswordLink_xpath");
			
			//Get the list of window handles
			ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
			System.out.println(newTab.size());
			driver.switchTo().window(newTab.get(1));
			
			test.log(Status.INFO, "Validating Reset password");
			type("ResetNewPassword_xpath","NewPassword");
			type("ResetConfirmPassword_xpath","ConfirmPassword");
			click("ResetSaveButton_xpath");
									
		}
		catch (Throwable t)
		{
			reportFailure("Failed to reset the password" );
		}
	}
	
	//Verify password changed notification is sent to user.
	public void verifyPasswordChangedNotification()
	{
		try
		{
		test.log(Status.INFO, "Validating forgot password email in mailinator");
		type("MailinatorMailinput_xpath","Email");
		click("MailinatorGoButton_xpath");
		click("MailinatorInbox_xpath");
		String Password_Changed = driver.findElement(By.xpath("//*[@class='single_mail']//ul/li[1]/div/div[4]")).getText();
		test.log(Status.INFO, "Reset Password Notification sent successfully with Subject : " + Password_Changed);
		
		}
		
		catch (Throwable t)
		{
			reportFailure("Failed to send the reset password email" );
		}
	}
	
	//Verify continue payment while new account creation
	public void VerifyNewAccountwithPayment()
	{
		try
		{
			test.log(Status.INFO, "Validate creation of a new Account with payment");
			click("CreateNewUser_xpath");
			type("groupcode_xpath","groupcode");
			click("NextButton_xpath");
			type("FirstName_xpath","FirstName");
			type("LastName_xpath","LastName");
			type("Email_xpath","Email");
			type("Userphone_xpath","Phone");
			click("NextButtonfirstpage_xpath");
			type("Password_xpath","Password");
			type("ConfirmPassword_xpath","ConfirmPassword");
			click("NextButtonpwd_xpath");
			type("FirstRepId_xpath","RepId1");
			type("SecondRepId_xpath","RepId2");
			type("AdditionalNotificationEmail_xpath","Additional Notification Email");
			click("CompleteButton_xpath");
			Thread.sleep(10000);
			click("UpgradeButton_xpath");
			test.log(Status.INFO, "Creation of a new Account upgradation is done successfully.");
		
		}
		catch (Throwable t)
		{
			reportFailure("Failed to do payment during account creation" );
		}
	}
	
	//Verify filling up the billing info during account creation
	public void VerifyFillingBillingInfo()
	{
		try
		{
		test.log(Status.INFO, " Validate Billing Info");
		clear("BillingInfoUserName_xpath");
		type("BillingInfoUserName_xpath","UserName");
		clear("BillingInfoEmail_xpath");
		type("BillingInfoEmail_xpath","Email");
		clear("BillingInfoPostalCode_xpath");
		type("BillingInfoPostalCode_xpath","PostalCode");
		clear("BillingInfoCardNumber_xpath");
		type("BillingInfoCardNumber_xpath","CardNumber");
		
		//Select the expiry month as '06' from the dropdowwn
 		Select expMonth = new Select(driver.findElement(By.id("expMonth")));
		expMonth.selectByVisibleText("06");
		
		//Select the expiry year as '2023' from the dropdowwn
 		Select expYear = new Select(driver.findElement(By.id("expYear")));
 		expYear.selectByVisibleText("2023");
 		
 		clear("BillingInfocvv_xpath");
 		type("BillingInfocvv_xpath","CVVCode");
 		click("PayAndCreateAccountButton_xpath");
 		test.log(Status.INFO, " Validate Billing Info");
 		}
		catch (Throwable t)
		{
			reportFailure("Failed to do successful payment during account creation" );
		}
		
	}
	
	//Verify new user account with payment is created 
	public void VerifyEmailFromInboxNewAccountwithPayment()
	{
		try
		{
		test.log(Status.INFO, "Validating Newly created account confirmation link has beem emailed in mailinator");
		type("MailinatorMailinput_xpath","Email");
		click("MailinatorGoButton_xpath");
		click("MailinatorInbox_xpath");
		String Count = driver.findElement(By.xpath("//*[@id='InboxCtrl']/section/div/div[2]/ul/li[1]/ul/li[1]//div[@class='ng-binding']")).getText();
		if(Count != null)
		{
			test.log(Status.INFO, "Newly created account confirmation link has been emailed successfully with email count in inbox : " +Count);
		}
		else
		test.log(Status.INFO, "Newly created account confirmation link has not been emailed to the user");
		click("MailinatorNewAccountwithPaymentMail_xpath");
		}
		
		catch (Throwable t)
		{
			reportFailure("Failed to get the email confirmation for newly created account with payment" );
		}
	}
	
	

	//Verify creation of new user account
	public void VerifyCreateNewAccount()
	{
		try 
		{
		test.log(Status.INFO, "Validate creation of New user account");
		click("CreateNewUser_xpath");
		type("groupcode_xpath","groupcode");
		click("NextButton_xpath");
		type("FirstName_xpath","FirstName");
		type("LastName_xpath","LastName");
		type("Email_xpath","Email");
		type("Userphone_xpath","Phone");
		click("NextButtonfirstpage_xpath");
		type("Password_xpath","Password");
		type("ConfirmPassword_xpath","ConfirmPassword");
		click("NextButtonpwd_xpath");
		type("FirstRepId_xpath","RepId1");
		type("SecondRepId_xpath","RepId2");
		type("AdditionalNotificationEmail_xpath","Additional Notification Email");
		click("CompleteButton_xpath");
		Thread.sleep(10000);
		click("NotYetButton_xpath");
		test.log(Status.INFO, "Validate creation of New user account not yet paid");
		}
		
		catch (Throwable t)
		{
			reportFailure("Failed to create a new account" );
		}
	}
	
	//Verify confirmation mail for user creation without payment is sent.
	public void VerifyEmailFromInboxNewAccount()
	{
		try
		{
		test.log(Status.INFO, "Validating Newly created account confirmation link has been emailed in mailinator");
		type("MailinatorMailinput_xpath","Email");
		click("MailinatorGoButton_xpath");
		click("MailinatorInbox_xpath");
		String Count = driver.findElement(By.xpath("//*[@id='InboxCtrl']/section/div/div[2]/ul/li[1]/ul/li[1]//div[@class='ng-binding']")).getText();
		if(Count != null)
		{
			test.log(Status.INFO, "Newly created account confirmation link has been emailed successfully with email count in inbox : " +Count);
		}
		else
			
		test.log(Status.INFO, "Newly created account confirmation link has not been emailed to the user");
		click("MailinatorNewAccountMail_xpath");
		test.log(Status.INFO, "Newly created account confirmation link has been emailed successfully");
		}
		
		catch (Throwable t)
		{
			reportFailure("Failed to get the email confirmation for newly created account" );
		}
			
	}
	
	//Verify Adding new contact 
	public void verifyAddingNewContact()
	{
		try
		{
		test.log(Status.INFO, " Validate adding a new contact");
		click("Contacts_xpath");
		click("CreateNewContactButton_xpath");
		type("ContactFirstname_xpath","FirstName");
		type("ContactLastname_xpath","LastName");
		type("ContactPhone_xpath","Phone");
		type("ContactEmailAddress_xpath","Email");
		type("ContactAddress1_xpath","Address1");
		type("ContactAddress2_xpath","Address2");
		type("ContactCity_xpath","City");
				
		//Select the country as 'United States' from the dropdowwn
 		Select country = new Select(driver.findElement(By.id("country")));
 		country.selectByVisibleText("United States");
		
		//Select the state as 'Alabama' from the dropdowwn
 		Select state = new Select(driver.findElement(By.id("state")));
 		state.selectByVisibleText("Alabama");
		
 		type("ContactPostalCode_xpath","PostalCode");
 		type("ContactCompanyName_xpath","CompanyName");
 		type("ContactTitle_xpath","Title");
		
		//Using AutoIt uploaded the image of the contact.
		driver.findElement(By.xpath("//label[text()='Choose a Picture']")).click();
		Runtime.getRuntime().exec("D:\\FileUpload.exe");
		Thread.sleep(5000);
		
		//Select the Rating as '2 Star' from the dropdowwn
 		Select interest = new Select(driver.findElement(By.id("interest")));
 		interest.selectByVisibleText("2 Star");
 		
 		type("ContactTag_xpath","Tag");
		click("TagSelection_xpath");
		type("ContactNote_xpath","Note");
		
		//Scroll down the web page by the visibility of the element
		JavascriptExecutor js = (JavascriptExecutor)driver;
		WebElement Element = driver.findElement(By.xpath("//*[@id='add-contact']/div[21]/div/button[1]"));
		js.executeScript("arguments[0].scrollIntoView();", Element);
		
		//Select the state as 'Opportunity Campaign' from the dropdowwn
 		Select campaignAlias = new Select(driver.findElement(By.id("campaign")));
 		campaignAlias.selectByVisibleText("QA Test A Campaign");
		
 		click("ContactSaveButton_xpath");
 		verifyText("ContactCreationSuccess_xpath",data.get("ExpectedText"));
 		test.log(Status.INFO, "Created a new contact successfully");
 		}
		
		catch (Throwable t)
		{
			test.log(Status.INFO, "Failed to Create a new contact");
			System.out.println("Exception occured : " +t.getMessage());
			reportFailure("Failed to Create a new contact");
		}
	}
	
	//Verify opt-in email in contact's mailbox
	public void VerifyOptinEmail()
	{
		try
		{
		test.log(Status.INFO, "Validating Opt-in email in mailinator");
		type("MailinatorMailinput_xpath","Email");
		click("MailinatorGoButton_xpath");
		click("MailinatorInbox_xpath");
		waitForPageToLoad();
		String Optin_Mail = driver.findElement(By.xpath("//*[@id='InboxCtrl']/section/div/div[2]/ul/li/ul/li/div/div[2]")).getText();
		System.out.println("Optin email has been sent as in the mailinator there are " + Optin_Mail + " email");
		if(Optin_Mail.equals(1))
			test.log(Status.INFO, "Optin email has been sent successfully");
		else
			reportFailure("Could not find the Optin email");
		
		}
		catch (Throwable t)
		{
			test.log(Status.INFO, "Failed to recieve Opt-in email in mailinator");
			System.out.println("Exception occured : " +t.getLocalizedMessage());
			reportFailure("Failed to recieve Opt-in email in mailinator");
		}
	
	}
	
	//Verify Re-optin email is sent to contact
	public void VerifyReOptinEmail()
	{
		try
		 {
			test.log(Status.INFO, " Validating Re-Optin email in mailinator");
			click("Contacts_xpath");
			type("ContactSearch_xpath","Email");
			click("ContactNormalSearchButton_xpath");
			waitForPageToLoad();
			Thread.sleep(20000);
			System.out.println("waited");
			driver.findElement(By.xpath("//*[@id='DataTables_Table_0']/tbody/tr[1]//td[@class='contactAction font-size-13']/div/a[3]/i")).click();
			
			//Confirm from the dialog box by pressing Enter key through selenium
			driver.findElement(By.xpath("//button[@class='btn btn-primary']")).sendKeys(Keys.ENTER);
			waitForPageToLoad();
			
			//Confirm from the dialog box by pressing Enter key through selenium
			driver.findElement(By.xpath("//button[@class='btn btn-primary']")).sendKeys(Keys.ENTER);
					
			test.log(Status.INFO, "Reoptin Email is sent successfully");	
			
			
		 }
		 catch (Throwable t)
			{
			 	reportFailure("Failed to search the newly added contact");
							
			}
		}
	
	//Verify Re-optin email in contact's mailbox
	public void VerifyReOptinEmailFromInbox()
	{
		try
		{
		test.log(Status.INFO, "Validating ReOpt-in email in mailinator");
		type("MailinatorMailinput_xpath","Email");
		click("MailinatorGoButton_xpath");
		click("MailinatorInbox_xpath");
		waitForPageToLoad();
		String Reoptin_Mail=driver.findElement(By.xpath("//*[@id='InboxCtrl']/section/div/div[2]/ul/li/ul/li/div/div[2]")).getText();
		System.out.println("Reoptin email has been sent as in the mailinator there are  " + Reoptin_Mail + " emails");
		if(Reoptin_Mail.equals(1))
			test.log(Status.INFO, "Re-Optin email has been sent successfully");
		else
			reportFailure("Could not find the Re-Optin email");
		
		}
		catch (Throwable t)
		{
			reportFailure("Failed to recieve Re-optin email");
			
		}
	}
	
	//Verify Resource link is emailed to contact
	public void VerifyResourceEmail()
	{
		try
		{
			test.log(Status.INFO, " Validate sending Resource Email");
			click("Resources_xpath");
			waitForPageToLoad();
			driver.findElement(By.xpath("//table[@id='DataTables_Table_0']/tbody/tr[1]/td[13]/div[@class='heightEllipse']/a[2]/i")).click();
			waitForPageToLoad();
			type("ContactEmailResource_xpath","Email");
			click("ResourceSendButton_xpath");
			test.log(Status.INFO, " Resource Email sent successfully");
		}
		catch (Throwable t)
		{
			reportFailure("Failed to send Resource Email");
			System.out.println("Exception occured : " +t.getMessage());
			
		}
	}
	
	//Verify resource link is sent to contact's mailbox
	public void VerifyResourceEmailFromInbox()
	{

		try
		{
		test.log(Status.INFO, "Validating Resource email in mailinator");
		type("MailinatorMailinput_xpath","Email");
		click("MailinatorGoButton_xpath");
		click("MailinatorInbox_xpath");
		waitForPageToLoad();
		String Resource_Mail=driver.findElement(By.xpath("//*[@id='InboxCtrl']/section/div/div[2]/ul/li/ul/li/div/div[2]")).getText();
		System.out.println("Resource email has been sent as in the mailinator there are  " + Resource_Mail + " email");
		if(Resource_Mail.equals(1))
			test.log(Status.INFO, "Resource email has been sent successfully");
		else
			reportFailure("Could not find the Resource email");
		test.log(Status.INFO, "Resource email is sent successfully");
		
		}
		catch (Throwable t)
		{
			reportFailure("Failed to receive Resource email");
		}
	}

}