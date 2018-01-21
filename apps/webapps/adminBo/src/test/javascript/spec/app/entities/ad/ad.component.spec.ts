/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { AdminBoTestModule } from '../../../test.module';
import { AdComponent } from '../../../../../../main/webapp/app/entities/ad/ad.component';
import { AdService } from '../../../../../../main/webapp/app/entities/ad/ad.service';
import { Ad } from '../../../../../../main/webapp/app/entities/ad/ad.model';

describe('Component Tests', () => {

    describe('Ad Management Component', () => {
        let comp: AdComponent;
        let fixture: ComponentFixture<AdComponent>;
        let service: AdService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AdminBoTestModule],
                declarations: [AdComponent],
                providers: [
                    AdService
                ]
            })
            .overrideTemplate(AdComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AdComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AdService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Ad(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.ads[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
