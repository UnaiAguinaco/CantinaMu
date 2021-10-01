import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

WebUI.openBrowser('')

WebUI.navigateToUrl('https://mu-jantokia.duckdns.org:8443/login')

WebUI.setText(findTestObject('Object Repository/Page_MU Cantine/input_Usuario_username'), 'admin')

WebUI.setEncryptedText(findTestObject('Object Repository/Page_MU Cantine/input_Contrasea_password'), 'RigbBhfdqOBHHoyB4ltw9Q==')

WebUI.sendKeys(findTestObject('Object Repository/Page_MU Cantine/input_Contrasea_password'), Keys.chord(Keys.ENTER))

WebUI.click(findTestObject('Object Repository/Page_MU Cantine/div_User Main_asideButton'))

WebUI.click(findTestObject('Object Repository/Page_MU Cantine/div_Administrar habitaciones'))

WebUI.click(findTestObject('Object Repository/Page_MU Cantine/i_User Main_fas fa-bars fa-4x'))

WebUI.click(findTestObject('Object Repository/Page_MU Cantine/div_Crear nueva habitacin'))

WebUI.setText(findTestObject('Object Repository/Page_MU Cantine/input_Nmero de habitacin_idRoom'), '10101')

WebUI.setText(findTestObject('Object Repository/Page_MU Cantine/input_Filas_rowCount'), '3')

WebUI.setText(findTestObject('Object Repository/Page_MU Cantine/input_Columnas_columnCount'), '5')

WebUI.click(findTestObject('Object Repository/Page_MU Cantine/input_Columnas_create_building-submit'))

WebUI.click(findTestObject('Object Repository/Page_MU Cantine/div_Notificaciones_mesa 0'))

WebUI.click(findTestObject('Object Repository/Page_MU Cantine/div_Notificaciones_mesa 0'))

WebUI.click(findTestObject('Object Repository/Page_MU Cantine/div_Notificaciones_mesa 0'))

WebUI.click(findTestObject('Object Repository/Page_MU Cantine/div_Notificaciones_mesa 0'))

WebUI.click(findTestObject('Object Repository/Page_MU Cantine/button_Confirmar'))

WebUI.click(findTestObject('Object Repository/Page_MU Cantine/div_User Main_asideButton'))

WebUI.click(findTestObject('Object Repository/Page_MU Cantine/p_Administrar habitaciones'))

