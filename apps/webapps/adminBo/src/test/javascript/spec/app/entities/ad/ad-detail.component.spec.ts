/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { AdminBoTestModule } from '../../../test.module';
import { AdDetailComponent } from '../../../../../../main/webapp/app/entities/ad/ad-detail.component';
import { AdService } from '../../../../../../main/webapp/app/entities/ad/ad.service';
import { Ad } from '../../../../../../main/webapp/app/entities/ad/ad.model';

describe('Component Tests', () => {

    describe('Ad Management Detail Component', () => {
        let comp: AdDetailComponent;
        let fixture: ComponentFixture<AdDetailComponent>;
        let service: AdService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AdminBoTestModule],
                declarations: [AdDetailComponent],
                providers: [
                    AdService
                ]
            })
            .overrideTemplate(AdDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AdDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AdService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Ad(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.ad).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
