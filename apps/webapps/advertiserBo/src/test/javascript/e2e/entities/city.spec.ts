import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('City e2e test', () => {

    let navBarPage: NavBarPage;
    let cityDialogPage: CityDialogPage;
    let cityComponentsPage: CityComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Cities', () => {
        navBarPage.goToEntity('city');
        cityComponentsPage = new CityComponentsPage();
        expect(cityComponentsPage.getTitle()).toMatch(/advertiserBoApp.city.home.title/);

    });

    it('should load create City dialog', () => {
        cityComponentsPage.clickOnCreateButton();
        cityDialogPage = new CityDialogPage();
        expect(cityDialogPage.getModalTitle()).toMatch(/advertiserBoApp.city.home.createOrEditLabel/);
        cityDialogPage.close();
    });

    it('should create and save Cities', () => {
        cityComponentsPage.clickOnCreateButton();
        cityDialogPage.setZipcodeInput('zipcode');
        expect(cityDialogPage.getZipcodeInput()).toMatch('zipcode');
        cityDialogPage.setNameInput('name');
        expect(cityDialogPage.getNameInput()).toMatch('name');
        cityDialogPage.setLonInput('5');
        expect(cityDialogPage.getLonInput()).toMatch('5');
        cityDialogPage.setLatInput('5');
        expect(cityDialogPage.getLatInput()).toMatch('5');
        cityDialogPage.countrySelectLastOption();
        cityDialogPage.save();
        expect(cityDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class CityComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-city div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class CityDialogPage {
    modalTitle = element(by.css('h4#myCityLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    zipcodeInput = element(by.css('input#field_zipcode'));
    nameInput = element(by.css('input#field_name'));
    lonInput = element(by.css('input#field_lon'));
    latInput = element(by.css('input#field_lat'));
    countrySelect = element(by.css('select#field_country'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setZipcodeInput = function(zipcode) {
        this.zipcodeInput.sendKeys(zipcode);
    }

    getZipcodeInput = function() {
        return this.zipcodeInput.getAttribute('value');
    }

    setNameInput = function(name) {
        this.nameInput.sendKeys(name);
    }

    getNameInput = function() {
        return this.nameInput.getAttribute('value');
    }

    setLonInput = function(lon) {
        this.lonInput.sendKeys(lon);
    }

    getLonInput = function() {
        return this.lonInput.getAttribute('value');
    }

    setLatInput = function(lat) {
        this.latInput.sendKeys(lat);
    }

    getLatInput = function() {
        return this.latInput.getAttribute('value');
    }

    countrySelectLastOption = function() {
        this.countrySelect.all(by.tagName('option')).last().click();
    }

    countrySelectOption = function(option) {
        this.countrySelect.sendKeys(option);
    }

    getCountrySelect = function() {
        return this.countrySelect;
    }

    getCountrySelectedOption = function() {
        return this.countrySelect.element(by.css('option:checked')).getText();
    }

    save() {
        this.saveButton.click();
    }

    close() {
        this.closeButton.click();
    }

    getSaveButton() {
        return this.saveButton;
    }
}
