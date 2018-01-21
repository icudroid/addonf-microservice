/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { AdvertiserBoTestModule } from '../../../test.module';
import { AdRuleDetailComponent } from '../../../../../../main/webapp/app/entities/ad-rule/ad-rule-detail.component';
import { AdRuleService } from '../../../../../../main/webapp/app/entities/ad-rule/ad-rule.service';
import { AdRule } from '../../../../../../main/webapp/app/entities/ad-rule/ad-rule.model';

describe('Component Tests', () => {

    describe('AdRule Management Detail Component', () => {
        let comp: AdRuleDetailComponent;
        let fixture: ComponentFixture<AdRuleDetailComponent>;
        let service: AdRuleService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AdvertiserBoTestModule],
                declarations: [AdRuleDetailComponent],
                providers: [
                    AdRuleService
                ]
            })
            .overrideTemplate(AdRuleDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AdRuleDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AdRuleService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new AdRule(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.adRule).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
