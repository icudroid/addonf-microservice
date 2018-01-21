import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('AdRule e2e test', () => {

    let navBarPage: NavBarPage;
    let adRuleDialogPage: AdRuleDialogPage;
    let adRuleComponentsPage: AdRuleComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load AdRules', () => {
        navBarPage.goToEntity('ad-rule');
        adRuleComponentsPage = new AdRuleComponentsPage();
        expect(adRuleComponentsPage.getTitle()).toMatch(/adminBoApp.adRule.home.title/);

    });

    it('should load create AdRule dialog', () => {
        adRuleComponentsPage.clickOnCreateButton();
        adRuleDialogPage = new AdRuleDialogPage();
        expect(adRuleDialogPage.getModalTitle()).toMatch(/adminBoApp.adRule.home.createOrEditLabel/);
        adRuleDialogPage.close();
    });

    it('should create and save AdRules', () => {
        adRuleComponentsPage.clickOnCreateButton();
        adRuleDialogPage.adSelectLastOption();
        adRuleDialogPage.save();
        expect(adRuleDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class AdRuleComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-ad-rule div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class AdRuleDialogPage {
    modalTitle = element(by.css('h4#myAdRuleLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    adSelect = element(by.css('select#field_ad'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    adSelectLastOption = function() {
        this.adSelect.all(by.tagName('option')).last().click();
    }

    adSelectOption = function(option) {
        this.adSelect.sendKeys(option);
    }

    getAdSelect = function() {
        return this.adSelect;
    }

    getAdSelectedOption = function() {
        return this.adSelect.element(by.css('option:checked')).getText();
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
