import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Sector e2e test', () => {

    let navBarPage: NavBarPage;
    let sectorDialogPage: SectorDialogPage;
    let sectorComponentsPage: SectorComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Sectors', () => {
        navBarPage.goToEntity('sector');
        sectorComponentsPage = new SectorComponentsPage();
        expect(sectorComponentsPage.getTitle()).toMatch(/advertiserBoApp.sector.home.title/);

    });

    it('should load create Sector dialog', () => {
        sectorComponentsPage.clickOnCreateButton();
        sectorDialogPage = new SectorDialogPage();
        expect(sectorDialogPage.getModalTitle()).toMatch(/advertiserBoApp.sector.home.createOrEditLabel/);
        sectorDialogPage.close();
    });

    it('should create and save Sectors', () => {
        sectorComponentsPage.clickOnCreateButton();
        sectorDialogPage.setCodeInput('code');
        expect(sectorDialogPage.getCodeInput()).toMatch('code');
        sectorDialogPage.setDescriptionInput('description');
        expect(sectorDialogPage.getDescriptionInput()).toMatch('description');
        sectorDialogPage.save();
        expect(sectorDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class SectorComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-sector div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class SectorDialogPage {
    modalTitle = element(by.css('h4#mySectorLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    codeInput = element(by.css('input#field_code'));
    descriptionInput = element(by.css('input#field_description'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setCodeInput = function(code) {
        this.codeInput.sendKeys(code);
    }

    getCodeInput = function() {
        return this.codeInput.getAttribute('value');
    }

    setDescriptionInput = function(description) {
        this.descriptionInput.sendKeys(description);
    }

    getDescriptionInput = function() {
        return this.descriptionInput.getAttribute('value');
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
