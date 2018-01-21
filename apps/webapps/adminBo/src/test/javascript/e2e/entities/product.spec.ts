import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
import * as path from 'path';
describe('Product e2e test', () => {

    let navBarPage: NavBarPage;
    let productDialogPage: ProductDialogPage;
    let productComponentsPage: ProductComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Products', () => {
        navBarPage.goToEntity('product');
        productComponentsPage = new ProductComponentsPage();
        expect(productComponentsPage.getTitle()).toMatch(/adminBoApp.product.home.title/);

    });

    it('should load create Product dialog', () => {
        productComponentsPage.clickOnCreateButton();
        productDialogPage = new ProductDialogPage();
        expect(productDialogPage.getModalTitle()).toMatch(/adminBoApp.product.home.createOrEditLabel/);
        productDialogPage.close();
    });

    it('should create and save Products', () => {
        productComponentsPage.clickOnCreateButton();
        productDialogPage.setNameInput('name');
        expect(productDialogPage.getNameInput()).toMatch('name');
        productDialogPage.setDescriptionInput('description');
        expect(productDialogPage.getDescriptionInput()).toMatch('description');
        productDialogPage.setPublicPriceInput('5');
        expect(productDialogPage.getPublicPriceInput()).toMatch('5');
        productDialogPage.setAdPriceInput('5');
        expect(productDialogPage.getAdPriceInput()).toMatch('5');
        productDialogPage.setImageInput(absolutePath);
        productDialogPage.adCampaingSelectLastOption();
        productDialogPage.brandSelectLastOption();
        productDialogPage.save();
        expect(productDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class ProductComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-product div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class ProductDialogPage {
    modalTitle = element(by.css('h4#myProductLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    nameInput = element(by.css('input#field_name'));
    descriptionInput = element(by.css('input#field_description'));
    publicPriceInput = element(by.css('input#field_publicPrice'));
    adPriceInput = element(by.css('input#field_adPrice'));
    imageInput = element(by.css('input#file_image'));
    adCampaingSelect = element(by.css('select#field_adCampaing'));
    brandSelect = element(by.css('select#field_brand'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setNameInput = function(name) {
        this.nameInput.sendKeys(name);
    }

    getNameInput = function() {
        return this.nameInput.getAttribute('value');
    }

    setDescriptionInput = function(description) {
        this.descriptionInput.sendKeys(description);
    }

    getDescriptionInput = function() {
        return this.descriptionInput.getAttribute('value');
    }

    setPublicPriceInput = function(publicPrice) {
        this.publicPriceInput.sendKeys(publicPrice);
    }

    getPublicPriceInput = function() {
        return this.publicPriceInput.getAttribute('value');
    }

    setAdPriceInput = function(adPrice) {
        this.adPriceInput.sendKeys(adPrice);
    }

    getAdPriceInput = function() {
        return this.adPriceInput.getAttribute('value');
    }

    setImageInput = function(image) {
        this.imageInput.sendKeys(image);
    }

    getImageInput = function() {
        return this.imageInput.getAttribute('value');
    }

    adCampaingSelectLastOption = function() {
        this.adCampaingSelect.all(by.tagName('option')).last().click();
    }

    adCampaingSelectOption = function(option) {
        this.adCampaingSelect.sendKeys(option);
    }

    getAdCampaingSelect = function() {
        return this.adCampaingSelect;
    }

    getAdCampaingSelectedOption = function() {
        return this.adCampaingSelect.element(by.css('option:checked')).getText();
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
