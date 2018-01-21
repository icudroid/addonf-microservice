/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { AdvertiserBoTestModule } from '../../../test.module';
import { AdRuleComponent } from '../../../../../../main/webapp/app/entities/ad-rule/ad-rule.component';
import { AdRuleService } from '../../../../../../main/webapp/app/entities/ad-rule/ad-rule.service';
import { AdRule } from '../../../../../../main/webapp/app/entities/ad-rule/ad-rule.model';

describe('Component Tests', () => {

    describe('AdRule Management Component', () => {
        let comp: AdRuleComponent;
        let fixture: ComponentFixture<AdRuleComponent>;
        let service: AdRuleService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AdvertiserBoTestModule],
                declarations: [AdRuleComponent],
                providers: [
                    AdRuleService
                ]
            })
            .overrideTemplate(AdRuleComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AdRuleComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AdRuleService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new AdRule(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.adRules[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
