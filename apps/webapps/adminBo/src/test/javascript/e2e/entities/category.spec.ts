import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Category e2e test', () => {

    let navBarPage: NavBarPage;
    let categoryDialogPage: CategoryDialogPage;
    let categoryComponentsPage: CategoryComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Categories', () => {
        navBarPage.goToEntity('category');
        categoryComponentsPage = new CategoryComponentsPage();
        expect(categoryComponentsPage.getTitle()).toMatch(/adminBoApp.category.home.title/);

    });

    it('should load create Category dialog', () => {
        categoryComponentsPage.clickOnCreateButton();
        categoryDialogPage = new CategoryDialogPage();
        expect(categoryDialogPage.getModalTitle()).toMatch(/adminBoApp.category.home.createOrEditLabel/);
        categoryDialogPage.close();
    });

    it('should create and save Categories', () => {
        categoryComponentsPage.clickOnCreateButton();
        categoryDialogPage.setKeyInput('key');
        expect(categoryDialogPage.getKeyInput()).toMatch('key');
        categoryDialogPage.setDescriptionInput('description');
        expect(categoryDialogPage.getDescriptionInput()).toMatch('description');
        categoryDialogPage.mainSelectLastOption();
        categoryDialogPage.save();
        expect(categoryDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class CategoryComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-category div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class CategoryDialogPage {
    modalTitle = element(by.css('h4#myCategoryLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    keyInput = element(by.css('input#field_key'));
    descriptionInput = element(by.css('input#field_description'));
    mainSelect = element(by.css('select#field_main'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setKeyInput = function(key) {
        this.keyInput.sendKeys(key);
    }

    getKeyInput = function() {
        return this.keyInput.getAttribute('value');
    }

    setDescriptionInput = function(description) {
        this.descriptionInput.sendKeys(description);
    }

    getDescriptionInput = function() {
        return this.descriptionInput.getAttribute('value');
    }

    mainSelectLastOption = function() {
        this.mainSelect.all(by.tagName('option')).last().click();
    }

    mainSelectOption = function(option) {
        this.mainSelect.sendKeys(option);
    }

    getMainSelect = function() {
        return this.mainSelect;
    }

    getMainSelectedOption = function() {
        return this.mainSelect.element(by.css('option:checked')).getText();
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
