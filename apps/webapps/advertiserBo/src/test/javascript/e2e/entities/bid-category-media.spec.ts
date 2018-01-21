import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('BidCategoryMedia e2e test', () => {

    let navBarPage: NavBarPage;
    let bidCategoryMediaDialogPage: BidCategoryMediaDialogPage;
    let bidCategoryMediaComponentsPage: BidCategoryMediaComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load BidCategoryMedias', () => {
        navBarPage.goToEntity('bid-category-media');
        bidCategoryMediaComponentsPage = new BidCategoryMediaComponentsPage();
        expect(bidCategoryMediaComponentsPage.getTitle()).toMatch(/advertiserBoApp.bidCategoryMedia.home.title/);

    });

    it('should load create BidCategoryMedia dialog', () => {
        bidCategoryMediaComponentsPage.clickOnCreateButton();
        bidCategoryMediaDialogPage = new BidCategoryMediaDialogPage();
        expect(bidCategoryMediaDialogPage.getModalTitle()).toMatch(/advertiserBoApp.bidCategoryMedia.home.createOrEditLabel/);
        bidCategoryMediaDialogPage.close();
    });

    it('should create and save BidCategoryMedias', () => {
        bidCategoryMediaComponentsPage.clickOnCreateButton();
        bidCategoryMediaDialogPage.setBidInput('5');
        expect(bidCategoryMediaDialogPage.getBidInput()).toMatch('5');
        bidCategoryMediaDialogPage.mediaTypeSelectLastOption();
        bidCategoryMediaDialogPage.adSelectLastOption();
        bidCategoryMediaDialogPage.categorySelectLastOption();
        bidCategoryMediaDialogPage.mediaSelectLastOption();
        bidCategoryMediaDialogPage.save();
        expect(bidCategoryMediaDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class BidCategoryMediaComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-bid-category-media div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class BidCategoryMediaDialogPage {
    modalTitle = element(by.css('h4#myBidCategoryMediaLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    bidInput = element(by.css('input#field_bid'));
    mediaTypeSelect = element(by.css('select#field_mediaType'));
    adSelect = element(by.css('select#field_ad'));
    categorySelect = element(by.css('select#field_category'));
    mediaSelect = element(by.css('select#field_media'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setBidInput = function(bid) {
        this.bidInput.sendKeys(bid);
    }

    getBidInput = function() {
        return this.bidInput.getAttribute('value');
    }

    setMediaTypeSelect = function(mediaType) {
        this.mediaTypeSelect.sendKeys(mediaType);
    }

    getMediaTypeSelect = function() {
        return this.mediaTypeSelect.element(by.css('option:checked')).getText();
    }

    mediaTypeSelectLastOption = function() {
        this.mediaTypeSelect.all(by.tagName('option')).last().click();
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

    categorySelectLastOption = function() {
        this.categorySelect.all(by.tagName('option')).last().click();
    }

    categorySelectOption = function(option) {
        this.categorySelect.sendKeys(option);
    }

    getCategorySelect = function() {
        return this.categorySelect;
    }

    getCategorySelectedOption = function() {
        return this.categorySelect.element(by.css('option:checked')).getText();
    }

    mediaSelectLastOption = function() {
        this.mediaSelect.all(by.tagName('option')).last().click();
    }

    mediaSelectOption = function(option) {
        this.mediaSelect.sendKeys(option);
    }

    getMediaSelect = function() {
        return this.mediaSelect;
    }

    getMediaSelectedOption = function() {
        return this.mediaSelect.element(by.css('option:checked')).getText();
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
