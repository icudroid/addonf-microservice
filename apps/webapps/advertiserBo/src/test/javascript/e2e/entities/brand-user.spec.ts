import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('BrandUser e2e test', () => {

    let navBarPage: NavBarPage;
    let brandUserDialogPage: BrandUserDialogPage;
    let brandUserComponentsPage: BrandUserComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load BrandUsers', () => {
        navBarPage.goToEntity('brand-user');
        brandUserComponentsPage = new BrandUserComponentsPage();
        expect(brandUserComponentsPage.getTitle()).toMatch(/advertiserBoApp.brandUser.home.title/);

    });

    it('should load create BrandUser dialog', () => {
        brandUserComponentsPage.clickOnCreateButton();
        brandUserDialogPage = new BrandUserDialogPage();
        expect(brandUserDialogPage.getModalTitle()).toMatch(/advertiserBoApp.brandUser.home.createOrEditLabel/);
        brandUserDialogPage.close();
    });

    it('should create and save BrandUsers', () => {
        brandUserComponentsPage.clickOnCreateButton();
        brandUserDialogPage.setUserIdInput('5');
        expect(brandUserDialogPage.getUserIdInput()).toMatch('5');
        brandUserDialogPage.brandSelectLastOption();
        brandUserDialogPage.save();
        expect(brandUserDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class BrandUserComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-brand-user div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class BrandUserDialogPage {
    modalTitle = element(by.css('h4#myBrandUserLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    userIdInput = element(by.css('input#field_userId'));
    brandSelect = element(by.css('select#field_brand'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setUserIdInput = function(userId) {
        this.userIdInput.sendKeys(userId);
    }

    getUserIdInput = function() {
        return this.userIdInput.getAttribute('value');
    }

    brandSelectLastOption = function() {
        this.brandSelect.all(by.tagName('option')).last().click();
    }

    brandSelectOption = function(option) {
        this.brandSelect.sendKeys(option);
    }

    getBrandSelect = function() {
        return this.brandSelect;
    }

    getBrandSelectedOption = function() {
        return this.brandSelect.element(by.css('option:checked')).getText();
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
